CREATE TABLE sys_fileloc (
  objid varchar(50) NOT NULL,
  url varchar(255) NOT NULL,
  rootdir varchar(255) NULL,
  defaultloc int NOT NULL,
  loctype varchar(20) NULL,
  user_name varchar(50) NULL,
  user_pwd varchar(50) NULL,
  info text,
  constraint pk_sys_fileloc PRIMARY KEY (objid)
) 
go 
create index ix_loctype on sys_fileloc (loctype)
go 


CREATE TABLE sys_file (
  objid varchar(50) NOT NULL,
  title varchar(50) NULL,
  filetype varchar(50) NULL,
  dtcreated datetime NULL,
  createdby_objid varchar(50) NULL,
  createdby_name varchar(255) NULL,
  keywords varchar(255) NULL,
  description text,
  constraint pk_sys_file PRIMARY KEY (objid)
) 
go 
create index ix_dtcreated on sys_file (dtcreated)
go 
create index ix_createdby_objid on sys_file (createdby_objid)
go 
create index ix_keywords on sys_file (keywords)
go 
create index ix_title on sys_file (title)
go 


CREATE TABLE sys_fileitem (
  objid varchar(50) NOT NULL,
  state varchar(50) NULL,
  parentid varchar(50) NULL,
  dtcreated datetime NULL,
  createdby_objid varchar(50) NULL,
  createdby_name varchar(255) NULL,
  caption varchar(155) NULL,
  remarks varchar(255) NULL,
  filelocid varchar(50) NULL,
  filesize int NULL,
  thumbnail text,
  constraint pk_sys_fileitem PRIMARY KEY (objid)
) 
go 
create index ix_parentid on sys_fileitem (parentid)
go 
create index ix_filelocid on sys_fileitem (filelocid)
go 
alter table sys_fileitem add CONSTRAINT fk_sys_fileitem_parentid 
  FOREIGN KEY (parentid) REFERENCES sys_file (objid)
go 
