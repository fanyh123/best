package com.ran.test.util;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterFactory implements FactoryBean<JedisCluster>, InitializingBean {
	
	private Resource resource;//加载集群配置文件
	private String addressKeyPrefix;//配置文件中获取地址的key的前缀
	
	private JedisCluster jedisCluster;
	private Integer connectionTimeout;
	private Integer maxAttempts;
    private String password;
	private Integer soTimeout;
	private GenericObjectPoolConfig poolConfig;//对象池化的配置commons-pool
	
	private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");
	
	private Set<HostAndPort> parseHostAndPort() throws Exception{
		try{
			Properties prop = new Properties();
			prop.load(this.resource.getInputStream());			
			Set<HostAndPort> haps = new HashSet<HostAndPort>();			
			for(Object key : prop.keySet()){
				if(!((String)key).startsWith(addressKeyPrefix)){
					continue;
				}
				String val = (String)prop.get(key);
				boolean isIpPort  = p.matcher(val).matches();
				if(!isIpPort){
					throw new IllegalArgumentException("ip 或 port 不合法");
				}
				String[] ipAndPort = val.split(":");
				HostAndPort hap = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
				haps.add(hap);
			}			
			return haps;
		}catch(IllegalArgumentException ex){
			throw ex;
		}catch(Exception ex){
			throw new Exception("解析redis配置文件出错",ex);
		}
	}

	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("JedisClusterFactory afterPropertiesSet......");
		Set<HostAndPort> haps = this.parseHostAndPort();
		jedisCluster = new JedisCluster(haps, connectionTimeout, soTimeout, maxAttempts, password, poolConfig);
	}

	public JedisCluster getObject() throws Exception {
		// TODO Auto-generated method stub
		return jedisCluster;
	}

	public Class<? extends JedisCluster> getObjectType() {
		// TODO Auto-generated method stub
		return (this.jedisCluster != null ? this.jedisCluster.getClass() : JedisCluster.class); 
	}

	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return true;
	}


	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public void setAddressKeyPrefix(String addressKeyPrefix) {
		this.addressKeyPrefix = addressKeyPrefix;
	}
	public void setJedisCluster(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}
	public void setConnectionTimeout(Integer connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	public void setMaxAttempts(Integer maxAttempts) {
		this.maxAttempts = maxAttempts;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setSoTimeout(Integer soTimeout) {
		this.soTimeout = soTimeout;
	}
	public void setPoolConfig(
			GenericObjectPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}
	public void setP(Pattern p) {
		this.p = p;
	}

}
