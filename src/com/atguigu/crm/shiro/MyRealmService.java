package com.atguigu.crm.shiro;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crm.entity.Authority;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.service.UserService;

@Service
public class MyRealmService extends AuthorizingRealm{

	@Autowired
	private UserService userService;
	
	//进行授权方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//通过PrincipalCollection调用getPrimaryPrincipal方法获取用户登入信息
		User user = (User)principals.getPrimaryPrincipal();
		
		//获取用户角色的权限集合
		List<Authority> authorities = user.getRole().getAuthorities();
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//把权限封装到SimpleAuthorizationInfo
		System.out.println("权限个数： "+authorities.size());
		for (Authority authority : authorities) {
			System.out.println("authorities= "+authority.getName());
			info.addRole(authority.getName());
		}
		
		return info;
	}

	
	//进行认证方法
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		User user = userService.getByUserName(username);
		
		if(user == null){
			throw new UnknownAccountException("该用户不存在.");
		}
		
		if(user.getEnabled() != 1){
			throw new LockedAccountException("该用户被锁定.");
		}
		
		Object principal = user;
		Object hashedCredentials = user.getPassword();
		ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
		String realmName = getName();
		
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, hashedCredentials, 
				credentialsSalt, realmName);
		
		return info;
	}
	
	//@PostConstruct 相当于配置文件中的 init-method
	@PostConstruct 
	public void initCredentialsMatcher(){
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("MD5");
		credentialsMatcher.setHashIterations(1024);
		setCredentialsMatcher(credentialsMatcher);
	}
	
	public static void main(String[] args){
		String algorithmName = "MD5";
		String source = "123456";
		ByteSource salt = ByteSource.Util.bytes("ceadfd47cdaa814c");
		int hashIterations = 1024;
		SimpleHash simp = new SimpleHash(algorithmName, source, salt, hashIterations);
		System.out.println(simp);
	}

}
