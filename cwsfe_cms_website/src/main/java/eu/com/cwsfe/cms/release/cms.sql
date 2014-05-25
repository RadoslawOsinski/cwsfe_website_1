create table CMS_AUTHORS (
  id numeric(4, 0) not null primary key,
  first_name varchar(100) not null,
  last_name varchar(100) not null,
  google_plus_author_link varchar(500),
  status char(1) not null
);
create sequence CMS_AUTHORS_S start 1 cache 1;

CREATE TABLE CMS_LANGUAGES (
  id     NUMERIC(6) PRIMARY KEY NOT NULL,
  code   CHAR(2)                NOT NULL,
  status CHAR(1)                NOT NULL,
  name   VARCHAR(75)            NOT NULL
);
create sequence CMS_LANGUAGES_S start 1 cache 1;
CREATE UNIQUE INDEX CMS_LANGUAGES_IDX ON CMS_LANGUAGES(code);

create table BLOG_POSTS (
  id numeric(6, 0) primary key,
  post_author_id numeric(6, 0) not null references CMS_AUTHORS(id),
  post_text_code varchar(100) not null,
  post_creation_date timestamp,
  status char(1) not null
);
create sequence BLOG_POSTS_S start 1 cache 1;
CREATE INDEX BLOG_POSTS_POST_AUTHOR_ID_INDEX ON BLOG_POSTS(post_author_id);

create table BLOG_POST_I18N_CONTENTS (
  id numeric(6, 0) primary key,
  post_id numeric(6, 0) not null references BLOG_POSTS(id),
  language_id numeric(3, 0) not null references cms_languages(id),
  post_title varchar(100) not null,
  post_shortcut text,
  post_description text,
  status char(1) not null
);
create sequence BLOG_POST_I18N_CONTENTS_S start 1 cache 1;
CREATE INDEX BLOG_POST_I18N_CONTENTS_POST_ID_INDEX ON BLOG_POST_I18N_CONTENTS(post_id);
CREATE INDEX BLOG_POST_I18N_CONTENTS_LANGUAGE_ID_INDEX ON BLOG_POST_I18N_CONTENTS(language_id);

create table BLOG_KEYWORDS (
  id numeric(6, 0) primary key,
  keyword_name varchar(100) not null unique,
  status char(1) not null
);
create sequence BLOG_KEYWORDS_S start 1 cache 1;

create table BLOG_POST_KEYWORDS (
  blog_post_id numeric(6, 0) not null references BLOG_POSTS(id),
  blog_keyword_id numeric(6, 0) not null references BLOG_KEYWORDS(id),
  primary key(blog_post_id, blog_keyword_id)
);

create table BLOG_POST_IMAGES (
  id numeric(12, 0) primary key,
  blog_post_id numeric(6, 0) not null references BLOG_POSTS(id),
  title varchar(100) not null,
  file_name varchar(250) not null,
  file_size numeric(12, 0) not null,
  width numeric(6, 0),
  height numeric(6, 0),
  mime_type varchar(100),
  content bytea not null,
  created timestamp not null,
  status char(1) not null
);
CREATE INDEX BLOG_POST_IMAGES_POST_ID_INDEX ON BLOG_POSTS(id);
create sequence BLOG_POST_IMAGES_S start 1 cache 1;

create table BLOG_POST_CODE (
  code_id varchar(100) primary key,
  blog_post_id numeric(6, 0) not null references BLOG_POSTS(id),
  code text not null,
  status char(1) not null
);
CREATE INDEX BLOG_POST_CODE_POST_ID_INDEX ON BLOG_POSTS(id);

create table CMS_FOLDERS (
  id numeric(6, 0) primary key,
  parent_id numeric(6, 0) references CMS_FOLDERS(ID),
  folder_name varchar(100) not null unique,
  order_number numeric(4,0),
  status char(1) not null
);
create sequence CMS_FOLDERS_S start 1 cache 1;

create table NEWS_TYPES (
  id numeric(6, 0) primary key,
  type varchar(100) not null unique,
  status char(1) not null
);
create sequence NEWS_TYPES_S start 1 cache 1;

create table CMS_NEWS (
  id numeric(6, 0) primary key,
  author_id numeric(6, 0) not null references CMS_AUTHORS(id),
  news_type_id numeric(6,0) not null references NEWS_TYPES(id),
  folder_id numeric(6,0) not null references CMS_FOLDERS(id),
  creation_date timestamp not null,
  news_code varchar(300) not null unique,
  status char(1) not null
);
create sequence CMS_NEWS_S start 1 cache 1;

create table CMS_NEWS_I18N_CONTENTS (
  id numeric(6, 0) primary key,
  news_id numeric(6, 0) not null references CMS_NEWS(id),
  language_id numeric(3, 0) not null references cms_languages(id),
  news_title varchar(100) not null,
  news_shortcut text,
  news_description text,
  status char(1) not null
);
create sequence CMS_NEWS_I18N_CONTENTS_S start 1 cache 1;

create table CMS_NEWS_IMAGES (
  id numeric(12, 0) primary key,
  news_id numeric(6, 0) not null references CMS_NEWS(id),
  title varchar(100) not null,
  file_name varchar(250) not null,
  file_size numeric(12, 0) not null,
  width numeric(6, 0),
  height numeric(6, 0),
  mime_type varchar(100),
  content bytea not null,
  created timestamp not null,
  status char(1) not null
);
CREATE INDEX CMS_NEWS_IMAGES_NEWS_ID_INDEX ON CMS_NEWS(id);
create sequence CMS_NEWS_IMAGES_S start 1 cache 1;

create table CMS_BLOG_POST_COMMENTS (
  id numeric(6, 0) primary key,
  parent_comment_id numeric(6, 0) references CMS_BLOG_POST_COMMENTS(id),
  blog_post_i18n_content_id numeric(6, 0) not null references BLOG_POST_I18N_CONTENTS(id),
  comment text not null,
  user_name varchar(250) not null,
  email varchar(300) not null,
  status char(1) not null,
  created timestamp not null
);
CREATE INDEX CMS_BLOG_POST_COMMENTS_parent_comment_id_INDEX ON CMS_BLOG_POST_COMMENTS(parent_comment_id);
CREATE INDEX CMS_BLOG_POST_COMMENTS_blog_post_i18n_content_id_INDEX ON CMS_BLOG_POST_COMMENTS(blog_post_i18n_content_id);
create sequence CMS_BLOG_POST_COMMENTS_S start 1 cache 1;


CREATE TABLE CMS_USERS (
  ID NUMERIC(10,0) NOT NULL PRIMARY KEY,
  USERNAME VARCHAR(100) NOT NULL,
  PASSWORD_HASH VARCHAR(1024) NOT NULL,
  STATUS CHAR(1) NOT NULL
);
create sequence CMS_USERS_S start 1 cache 1;

CREATE TABLE CMS_ROLES (
  ID NUMERIC(4, 0) NOT NULL PRIMARY KEY,
  ROLE_CODE VARCHAR(100) NOT NULL,
  ROLE_NAME VARCHAR(200) NOT NULL
);
create sequence CMS_ROLES_S start 1 cache 1;

INSERT INTO CMS_ROLES(ID, ROLE_CODE, ROLE_NAME) VALUES (NEXTVAL('CMS_ROLES_S'), 'ROLE_CWSFE_CMS_ADMIN', 'ROLE_CWSFE_CMS_ADMIN');

CREATE TABLE CMS_USER_ROLES (
  CMS_USER_ID NUMERIC(10, 0) NOT NULL REFERENCES CMS_USERS(ID),
  ROLE_ID NUMERIC (4,0) NOT NULL REFERENCES CMS_ROLES(ID),
  PRIMARY KEY (CMS_USER_ID, ROLE_ID)
);

INSERT INTO CMS_USER_ROLES(CMS_USER_ID, ROLE_ID) VALUES (1, 1);

CREATE TABLE CMS_GLOBAL_PARAMS (
  ID NUMERIC(4, 0) NOT NULL PRIMARY KEY,
  CODE VARCHAR(200) NOT NULL UNIQUE,
  DEFAULT_VALUE VARCHAR(400) NOT NULL,
  VALUE VARCHAR(400) NOT NULL,
  DESCRIPTION VARCHAR(1000) NOT NULL
);
create sequence CMS_GLOBAL_PARAMS_S start 1 cache 1;
INSERT INTO CMS_GLOBAL_PARAMS(ID, CODE, DEFAULT_VALUE, VALUE, DESCRIPTION) VALUES (
  nextval('CMS_GLOBAL_PARAMS_S'), 'CWSFE_CMS_VERSION', '1.0', '1.0', 'CWSFE CMS VERSION'
);
INSERT INTO CMS_GLOBAL_PARAMS(ID, CODE, DEFAULT_VALUE, VALUE, DESCRIPTION) VALUES (
  nextval('CMS_GLOBAL_PARAMS_S'), 'MAIN_SITE', 'http://cwsfe.eu', 'http://cwsfe.eu', 'MAIN WEBSITE OUTSIDE OF CWSFE CMS WEBSITE'
);
INSERT INTO CMS_GLOBAL_PARAMS(ID, CODE, DEFAULT_VALUE, VALUE, DESCRIPTION) VALUES (
  nextval('CMS_GLOBAL_PARAMS_S'), 'CWSFE_CMS_IS_CONFIGURED', 'N', 'N', 'IS CWSFE CMS CONFIGURED: "Y"es or "N"o'
);
-- INSERT INTO CMS_USERS(ID, USERNAME, PASSWORD_HASH, STATUS) VALUES (NEXTVAL('CMS_USERS_S'), 'rosinski', 'x', 'N');

CREATE TABLE CMS_TEXT_I18N_CATEGORIES (
  ID NUMERIC(3,0) NOT NULL PRIMARY KEY,
  CATEGORY VARCHAR(100) NOT NULL,
  STATUS CHAR(1) NOT NULL
);
CREATE SEQUENCE CMS_TEXT_I18N_CATEGORIES_S START 1 CACHE 1;

CREATE TABLE CMS_TEXT_I18N (
  ID NUMERIC(6,0) NOT NULL PRIMARY KEY,
  LANG_ID NUMERIC(6) NOT NULL REFERENCES CMS_LANGUAGES(ID),
  I18N_CATEGORY NUMERIC(3,0) NOT NULL REFERENCES CMS_TEXT_I18N_CATEGORIES(ID),
  I18N_KEY VARCHAR(100) NOT NULL,
  I18N_TEXT VARCHAR(500),
  UNIQUE (LANG_ID, I18N_CATEGORY, I18N_KEY)
);
CREATE SEQUENCE CMS_TEXT_I18N_S START 1 CACHE 1;

CREATE TABLE NEWSLETTER_MAIL_GROUPS (
  ID NUMERIC(6,0) NOT NULL PRIMARY KEY,
  LANGUAGE_ID numeric(3, 0) not null references cms_languages(id),
  NAME VARCHAR(100) NOT NULL,
  STATUS CHAR(1) NOT NULL
);
CREATE SEQUENCE NEWSLETTER_MAIL_GROUPS_S START 1 CACHE 1;

CREATE TABLE NEWSLETTER_MAIL_ADDRESSES (
  ID NUMERIC(6,0) NOT NULL PRIMARY KEY,
  MAIL_GROUP_ID NUMERIC(6,0) NOT NULL REFERENCES NEWSLETTER_MAIL_GROUPS(ID),
  CONFIRM_STRING VARCHAR(36) NOT NULL UNIQUE,
  UN_SUBSCRIBE_STRING VARCHAR(36) NOT NULL UNIQUE,
  EMAIL VARCHAR(300) NOT NULL,
  STATUS CHAR(1) NOT NULL,
  UNIQUE (MAIL_GROUP_ID, EMAIL)
);
CREATE SEQUENCE NEWSLETTER_MAIL_ADDRESSES_S START 1 CACHE 1;
CREATE INDEX NEWSLETTER_MAIL_ADDRESSES_MAIL_GROUP_ID_INDEX ON NEWSLETTER_MAIL_ADDRESSES(MAIL_GROUP_ID);
CREATE INDEX NEWSLETTER_MAIL_ADDRESSES_CONFIRM_STRING_INDEX ON NEWSLETTER_MAIL_ADDRESSES(CONFIRM_STRING);
CREATE INDEX NEWSLETTER_MAIL_ADDRESSES_UN_SUBSCRIBE_STRING_INDEX ON NEWSLETTER_MAIL_ADDRESSES(UN_SUBSCRIBE_STRING);

CREATE TABLE NEWSLETTER_TEMPLATES (
  ID NUMERIC(4,0) NOT NULL PRIMARY KEY,
  LANGUAGE_ID numeric(3, 0) not null references cms_languages(id),
  NAME VARCHAR(100) NOT NULL,
  STATUS CHAR(1) NOT NULL,
  SUBJECT VARCHAR(300) NOT NULL,
  CONTENT TEXT NOT NULL DEFAULT ''
);
CREATE SEQUENCE NEWSLETTER_TEMPLATES_S START 1 CACHE 1;

CREATE TABLE NEWSLETTER_MAIL (
  ID NUMERIC(6,0) NOT NULL PRIMARY KEY,
  RECIPIENT_GROUP_ID NUMERIC(6,0) REFERENCES NEWSLETTER_MAIL_GROUPS(ID) NOT NULL,
  NAME VARCHAR(100) NOT NULL,
  SUBJECT VARCHAR(300) NOT NULL,
  MAIL_CONTENT TEXT NOT NULL DEFAULT '',
  STATUS CHAR(1) NOT NULL
);
CREATE SEQUENCE NEWSLETTER_MAIL_S START 1 CACHE 1;

CREATE TABLE NEWSLETTER_DISPATCHED_MAILS (
  ID NUMERIC(6,0) NOT NULL PRIMARY KEY,
  NEWSLETTER_MAIL_ID NUMERIC(6,0) REFERENCES NEWSLETTER_MAIL(ID) NOT NULL,
  EMAIL VARCHAR(300) NOT NULL,
  ERROR VARCHAR(500),
  STATUS CHAR(1) NOT NULL
);
CREATE SEQUENCE NEWSLETTER_DISPATCHED_MAILS_S START 1 CACHE 1;
