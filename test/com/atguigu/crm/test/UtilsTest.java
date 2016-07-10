package com.atguigu.crm.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.atguigu.crm.utils.DataUtils;

public class UtilsTest {

	@Test
	public void test() {
		String uuid = DataUtils.getUUID();
		System.out.println(uuid);
	}

}
