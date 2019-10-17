drop table userInfo;

create table userInfo(
  usid int primary key,  --用户编号
  uname varchar2(40) unique,  --用户名
  pwd varchar2(100), --密码
  email varchar2(50) not null unique  --注册邮箱
);

drop sequence seq_usid;
create sequence seq_usid start with 10001;

create or replace trigger tri_userInfo_usid
before insert on userInfo
for each row
begin
  select seq_usid.nextval into :new.usid from dual;
end;


insert into userInfo values(1,'a','a','qfxsxhfy@163.com');
commit;
select * from userInfo;

drop table topic;
--新闻类型表
create table topic(
  tid int primary key,  --类型编号 
  tname varchar2(50)   --类型名称
  --status int  --为0表示不可用   1表示可用
);

create sequence seq_tid start with 10001;

create or replace trigger tri_topic_tid 
before insert on topic --对news表进行添加操作之前触发
for each row    --触发频率，每影响一行触发一次
begin
  select seq_tid.nextval into :new.tid from dual;
end;

insert into topic values(1,'国内');
insert into topic values(1,'国际');
insert into topic values(1,'军事');

select * from topic;

--新闻信息表
create table news(
  nid int primary key,  --新闻编号
  tid int
    constraint FK_news_topic references topic(tid) on delete cascade,  --新闻类型
  title varchar2(200 char), --新闻标题
  author varchar2(50 char), --新闻作者
  createDate date, --创建时间
  pic varchar2(200),  --图片地址
  content clob  --新闻内容
);
create sequence seq_nid start with 10001;

create or replace trigger tri_news_nid
before insert on news
for each row
begin
  select seq_nid.nextval into :new.nid from dual;
end;

select * from topic;
select * from news;

commit;

select * from (
	select nid,n.tid,title,author,to_char(createDate,'YYYY-MM-DD') as createDate,pic,content,tname,rownum rn 
		from news n,topic t where n.tid=t.tid and rownum<=10 order by createDate desc) where rn>5;
		
select * from (
	  select aa.*,rownum rn from(select nid,n.tid,title,author,to_char(createDate,'YYYY-MM-DD') as createDate,pic,content,tname
		 from news n,topic t where n.tid=t.tid order by createDate desc)aa);