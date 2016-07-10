package com.atguigu.crm.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.crm.entity.Authority;
import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.entity.CustomerService;
import com.atguigu.crm.entity.Product;
import com.atguigu.crm.entity.Role;
import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.mapper.ContactMapper;
import com.atguigu.crm.mapper.CustomerMapper;
import com.atguigu.crm.mapper.SalesChanceMapper;
import com.atguigu.crm.mapper.UserMapper;
import com.atguigu.crm.service.AuthorityService;
import com.atguigu.crm.service.CustomerServicesService;
import com.atguigu.crm.service.ProductService;
import com.atguigu.crm.service.SalesChanceService;
import com.atguigu.crm.service.UserService;
import com.atguigu.crm.utils.DataUtils;

public class ApplicationContextTest {

	private ApplicationContext ctx = null;
	private UserMapper userMapper = null;
	private SalesChanceMapper salesChanceMapper = null;
	private ContactMapper contactMapper = null;
	private CustomerMapper customerMapper = null;
	private SalesChanceService salesChanceService = null;
	private ProductService productService = null;
	private UserService userService = null;
	private AuthorityService authorityService = null;
	private CustomerServicesService customerServicesService = null;
	{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		userMapper = ctx.getBean(UserMapper.class);
		salesChanceMapper = ctx.getBean(SalesChanceMapper.class);
		contactMapper = ctx.getBean(ContactMapper.class);
		customerMapper = ctx.getBean(CustomerMapper.class);
		salesChanceService = ctx.getBean(SalesChanceService.class);
		productService = ctx.getBean(ProductService.class);
		userService = ctx.getBean(UserService.class);
		authorityService = ctx.getBean(AuthorityService.class);
		customerServicesService = ctx.getBean(CustomerServicesService.class);
	}
	
	@Test
	public void testDate(){
		
		System.out.println(new Date(0));
	}
	
	@Test
	public void saveServiceTest12(){
		CustomerService cs = new CustomerService();
		Customer ct = new Customer();
		ct.setId(2L);
		cs.setServiceType("建议");
		cs.setServiceTitle("shuibian");
		cs.setCustomer(ct);
		cs.setServiceState("新创建");
		cs.setServiceRequest("henhoa");
		cs.setCreateDate(new Date(0));
		
		customerServicesService.saveService(cs);
		
		
	}
	
	@Test
	public void getAuthorTest(){
		List<Authority> saveAuthority = authorityService.saveAuthority();
		for (Authority aut : saveAuthority) {
			System.out.println(aut.getDisplayName()+"("+aut.getId()+")");
			for (Authority sub : aut.getSubAuthorities()) {
				System.out.println("\t"+sub.getDisplayName()+"("+sub.getId()+")");
			}
		}
		
	}
	
	@Test
	public void getByIdUser(){
		User user = userService.getById(2300L);
		
	}
	
	@Test
	public void toAddUI(){
		List<Role> roles = userService.getRoles();
		System.out.println(roles);
		
	}
	
	@Test
	public void toUnpate(){
		Product byId = productService.getById(6l);
		System.out.println(byId.getName());
	}
	
	@Test
	public void test11() {
		salesChanceService.updateFinishStatus(new SalesChance());
	}
	
	@Test
	public void testSelectKey() {
		Customer customer = new Customer();
		customer.setNo(DataUtils.getUUID());
		customer.setName("hahahaha");
		customer.setState("正常");
		
		int i = customerMapper.insertCustomerForFinisChance(customer);
		System.out.println(i);
		
		/*Contact contact = new Contact();
		contact.setName("11111");
		contact.setTel("123123123123");
		int i = contactMapper.insertContactForFinshChance(contact);
		System.out.println(i);*/
	}
	
	@Test
	public void testSalesChanceMapperGetTotalElements2(){
		Map<String, Object> params = new HashMap<String, Object>();
		User createBy = new User();
		createBy.setId(24L);
		params.put("createBy", createBy);
		
		int status = 1;
		params.put("status", status);
		
		params.put("custName", "%��%");
		
		long result = salesChanceMapper.getTotalElements2(params);
		System.out.println(result);
	}
	
	@Test
	public void testSalesChanceMapperGetTotalElements(){
		User createBy = new User();
		createBy.setId(24L);
		int status = 1;
		
		long result = salesChanceMapper.getTotalElements(createBy, status);
		System.out.println(result);
	}
	
	@Test
	public void testUserMapper(){
		User user = userMapper.getByName("admin");
		System.out.println(user.getPassword());
		System.out.println(user.getRole().getName());
	}
	
	@Test
	public void testDataSource() throws SQLException {
		DataSource dataSource = ctx.getBean(DataSource.class);
		Connection connection = dataSource.getConnection();
		
		System.out.println(connection);
	}

}
