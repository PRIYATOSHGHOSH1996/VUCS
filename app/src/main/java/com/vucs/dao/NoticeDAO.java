package com.vucs.dao;


import com.vucs.model.NoticeModel;

import java.util.List;

import androidx.lifecycle.LiveData;



public interface NoticeDAO {


    public List<NoticeModel> getAllNotice();


    public void insertNotice(NoticeModel noticeModel);
}
