package com.atwj.reggie.service.impl;

import com.atwj.reggie.entity.AddressBook;
import com.atwj.reggie.mapper.AddressBookMapper;
import com.atwj.reggie.service.AddressBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author: wj
 * @create_time: 2022/11/17 20:02
 * @explain:
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
