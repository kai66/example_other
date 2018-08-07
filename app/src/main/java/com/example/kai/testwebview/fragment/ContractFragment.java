package com.example.kai.testwebview.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kai.testwebview.R;
import com.example.kai.testwebview.adapter.JoneBaseAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by kai on 2018/8/6.
 */

public class ContractFragment extends BaseFragment{

    Button bt_start;
    RecyclerView recycle_view;
    List<ContactsInfo> ContactsInfoDatas = new ArrayList<>();
    JoneBaseAdapter<ContactsInfo> joneBaseAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_contract,container,false);
        bt_start = (Button)view.findViewById(R.id.bt_start);
        recycle_view = (RecyclerView)view.findViewById(R.id.recycle_view);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity,LinearLayoutManager.VERTICAL,false);
        recycle_view.setLayoutManager(linearLayoutManager);
        joneBaseAdapter = new JoneBaseAdapter<ContactsInfo>(recycle_view,R.layout.item_contarct) {
            @Override
            public void fillItemData(BGAViewHolderHelper helper, int position, ContactsInfo model) {
                helper.setText(R.id.tv_name,model.getFullName());
                helper.setText(R.id.tv_tel_num,model.getMobile1());
            }
        };
        recycle_view.setAdapter(joneBaseAdapter);
        joneBaseAdapter.setData(ContactsInfoDatas);

        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContacts();
                Cursor cursor = null;
                ContactsInfoDatas.clear();
                try {
                    Uri contactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                    cursor = _mActivity.getContentResolver().query(contactUri,
                            new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.CONTACT_ID,ContactsContract.CommonDataKinds.Phone.NUMBER},
                            null, null, "");
                    String contactName;
                    String contactNumber;
                    //String contactSortKey;
                    String contactId;
                    while (cursor.moveToNext()) {
                        contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

                        contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        if(contactNumber!= null){
                            if(contactNumber.startsWith("+86")){
                                contactNumber = contactNumber.substring(3,contactNumber.length());
                            }
                            contactNumber = contactNumber.replaceAll("-","");
                            contactNumber = contactNumber.replace("(","");
                            contactNumber = contactNumber.replace(")","");
                            contactNumber = contactNumber.replace(" ", "");
                        }
                        Log.v("TAG","contactName="+contactName+" contactNumber="+contactNumber+" numlength="+contactNumber.length()+" contactId="+contactId);
                        //contactSortKey = getSortkey(cursor.getString(1));
                        ContactsInfo contactsInfo = new ContactsInfo();
                        contactsInfo.setFullName(contactName);
                        contactsInfo.setMobile1(contactNumber);
                        ContactsInfoDatas.add(contactsInfo);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                 if(cursor!=null){
                     cursor.close();
                 }
                }
                joneBaseAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getContacts() {
        //联系人的Uri，也就是content://com.android.contacts/contacts
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //指定获取_id和display_name两列数据，display_name即为姓名
        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        //根据Uri查询相应的ContentProvider，cursor为获取到的数据集
        Cursor cursor = _mActivity.getContentResolver().query(uri, projection, null, null, null);
       ArrayList<ContactsInfo> contactsInfos = new ArrayList<>();
        int i = 0;
        if (cursor != null && cursor.moveToFirst()) {
            ContactsInfo  contactsInfo = new ContactsInfo();
            do {
                String id = cursor.getString(0);
                //获取姓名
                String name = cursor.getString(1);
                //指定获取NUMBER这一列数据
                String[] phoneProjection = new String[] {
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                };
                contactsInfo.setContactId(id);
                contactsInfo.setFullName(name);
                //arr[i] = id + " , 姓名：" + name;
                Log.v("TAG","kevin id="+id);
                //根据联系人的ID获取此人的电话号码
                Cursor phonesCusor = _mActivity.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        phoneProjection,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                        null,
                        null);

                //因为每个联系人可能有多个电话号码，所以需要遍历
                String telNum="";
                if (phonesCusor != null && phonesCusor.moveToFirst()) {
                    do {
                        String num = phonesCusor.getString(0);
                        if(num!= null){
                            if(num.startsWith("+86")){
                                num = num.substring(3,num.length());
                            }
                            num = num.replaceAll("-","");
                            num = num.replace("(","");
                            num = num.replace(")","");
                            num = num.replace(" ", "");
                        }
                        telNum = telNum+num+",";
                        //arr[i] += " , 电话号码：" + num;
                    }while (phonesCusor.moveToNext());
                }
                if(telNum.contains(",")) {
                    telNum = telNum.substring(0,telNum.lastIndexOf(","));
                }
                Log.v("TAG","kevin telNum="+telNum);
                if(TextUtils.isEmpty(telNum)){
                    contactsInfo.setMobile1("");
                    contactsInfo.setMobile2("");
                    contactsInfo.setMobile3("");
                }else{
                    String[] names =  telNum.split(",");
                    int length =  names.length;
                    contactsInfo.setMobile1(length>=1?names[0]:"");
                    contactsInfo.setMobile2(length>=2?names[1]:"");
                    contactsInfo.setMobile3(length>=3?names[2]:"");
                }
                Log.v("TAG","kevin mobile1="+contactsInfo.getMobile1()+" mobile2="+contactsInfo.getMobile2()+" mobile3="+contactsInfo.getMobile3()
                        +" name="+contactsInfo.getFullName());
                i++;
            } while (cursor.moveToNext());
        }
        //return arr;
    }

    public class ContactsInfo implements Serializable{

       private  String fullName;
       private  String mobile1;
       private String mobile2;
       private String mobile3;
       private String contactId;

        public ContactsInfo (){

        }
        public String getContactId() {
            return contactId;
        }

        public void setContactId(String contactId) {
            this.contactId = contactId;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getMobile1() {
            return mobile1;
        }

        public void setMobile1(String mobile1) {
            this.mobile1 = mobile1;
        }

        public String getMobile2() {
            return mobile2;
        }

        public void setMobile2(String mobile2) {
            this.mobile2 = mobile2;
        }

        public String getMobile3() {
            return mobile3;
        }

        public void setMobile3(String mobile3) {
            this.mobile3 = mobile3;
        }
    }


}
