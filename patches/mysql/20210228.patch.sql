CREATE TABLE `sys_fileloc` (
  `objid` varchar(50) NOT NULL,
  `url` varchar(255) NOT NULL,
  `rootdir` varchar(255) NULL,
  `defaultloc` int NOT NULL,
  `loctype` varchar(20) NULL,
  `user_name` varchar(50) NULL,
  `user_pwd` varchar(50) NULL,
  `info` text,
  PRIMARY KEY (`objid`),
  KEY `ix_loctype` (`loctype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sys_file` (
  `objid` varchar(50) NOT NULL,
  `title` varchar(50) NULL,
  `filetype` varchar(50) NULL,
  `dtcreated` datetime NULL,
  `createdby_objid` varchar(50) NULL,
  `createdby_name` varchar(255) NULL,
  `keywords` varchar(255) NULL,
  `description` text,
  PRIMARY KEY (`objid`),
  KEY `ix_dtcreated` (`dtcreated`),
  KEY `ix_createdby_objid` (`createdby_objid`),
  KEY `ix_keywords` (`keywords`),
  KEY `ix_title` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sys_fileitem` (
  `objid` varchar(50) NOT NULL,
  `state` varchar(50) NULL,
  `parentid` varchar(50) NULL,
  `dtcreated` datetime NULL,
  `createdby_objid` varchar(50) NULL,
  `createdby_name` varchar(255) NULL,
  `caption` varchar(155) NULL,
  `remarks` varchar(255) NULL,
  `filelocid` varchar(50) NULL,
  `filesize` int NULL,
  `thumbnail` text,
  PRIMARY KEY (`objid`),
  KEY `ix_parentid` (`parentid`),
  KEY `ix_filelocid` (`filelocid`),
  CONSTRAINT `fk_sys_fileitem_parentid` FOREIGN KEY (`parentid`) REFERENCES `sys_file` (`objid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

