package com.atwj.reggie.controller;

import com.atwj.reggie.common.BaseContext;
import com.atwj.reggie.common.R;
import com.atwj.reggie.entity.AddressBook;
import com.atwj.reggie.service.AddressBookService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: wj
 * @create_time: 2022/11/17 20:03
 * @explain:
 */
@RestController
@Slf4j
@RequestMapping("/addressBook")
public class AddressBookController {
    @Resource
    private AddressBookService addressBookService;

    @GetMapping("/default")
    public R<AddressBook> getDefaultAddressApi() {
        log.info("获取默认地址");
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        //1表示是默认地址
        queryWrapper.eq(AddressBook::getIsDefault, 1);
        AddressBook addressBook = addressBookService.getOne(queryWrapper);
        return R.success(addressBook);

    }

    @PutMapping("/default")
    public R<String> setDefaultAddressApi(@RequestBody Long id) {
        log.info("设置默认地址");
        //获取登入人id
        Long userId = BaseContext.getCurrentId();
        //update address_book set is_default = 0 where user_id =
        //清除所有默认地址
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, userId);
        AddressBook addressBook = new AddressBook();
        addressBook.setIsDefault(0);
        addressBookService.update(addressBook, queryWrapper);
        //更新默认地址
        LambdaQueryWrapper<AddressBook> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(AddressBook::getId, id);
        addressBook.setId(id);
        addressBook.setIsDefault(1);
        addressBookService.updateById(addressBook);
        return R.success("设置默认地址成功");
    }

    @GetMapping("/{id}")
    public R<AddressBook> addressFindOneApi(@PathVariable("id") Long id) {
        log.info("查询单个收获地址");
        AddressBook addressBook = addressBookService.getById(id);
        return R.success(addressBook);
    }

    @DeleteMapping
    public R<String> deleteAddressApi(@RequestBody Long ids) {
        log.info("删除地址");
        addressBookService.removeById(ids);
        return R.success("删除地址成功");
    }

    @PutMapping
    public R<String> updateAddressApi(@RequestBody AddressBook addressBook) {
        log.info("修改地址");
        addressBookService.updateById(addressBook);
        return R.success("编辑地址成功");
    }

    @PostMapping
    public R<String> addAddressApi(@RequestBody AddressBook addressBook) {
        log.info("新增地址");
        addressBookService.save(addressBook);
        return R.success("新增地址成功");
    }

    @GetMapping("/list")
    public R<List<AddressBook>> addressListApi() {
        log.info("获取所有地址");
        List<AddressBook> addressBookList = addressBookService.list();
        return R.success(addressBookList);
    }
}
