insert into company(`company_id`, `company_name`, `company_country`, `company_location`)
 values (1, '원티드랩', '한국', '서울');

insert into company(`company_id`, `company_name`, `company_country`, `company_location`)
 values (2, '원티드코리아', '한국', '부산');

insert into company(`company_id`, `company_name`, `company_country`, `company_location`)
 values (3, '네이버', '한국', '판교');

insert into company(`company_id`, `company_name`, `company_country`, `company_location`)
 values (4, '카카오', '한국', '판교');

insert into recruitment(`recruitment_id`, `company_id`, `recruitment_position`, `recruitment_compensation`, `recruitment_content`, `recruitment_technology`)
 values (1, 1, '백엔드 주니어 개발자', 1500000, '원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..', 'Python');

insert into recruitment(`recruitment_id`, `company_id`, `recruitment_position`, `recruitment_compensation`, `recruitment_content`, `recruitment_technology`)
 values (2, 3, 'Django 백엔드 개발자', 1000000, '네이버에서 Django 백엔드 개발자를 채용합니다. 자격요건은..','Django');

insert into recruitment(`recruitment_id`, `company_id`, `recruitment_position`, `recruitment_compensation`, `recruitment_content`, `recruitment_technology`)
 values (3, 2, '프론트엔드 개발자', 500000, '원티드코리아에서 프론트엔드 시니어 개발자를 채용합니다. 자격요건은..', 'javascript');

insert into recruitment(`recruitment_id`, `company_id`, `recruitment_position`, `recruitment_compensation`, `recruitment_content`, `recruitment_technology`)
 values (4, 4, 'Django 백엔드 개발자', 500000, '카카오에서 Django 백엔드 개발자를 채용합니다. 자격요건은..', 'Python');

insert into recruitment(`recruitment_id`, `company_id`, `recruitment_position`, `recruitment_compensation`, `recruitment_content`, `recruitment_technology`)
 values (5, 2, 'Spring 백엔드 개발자', 1000000, '원티드코리아에서 자바 백엔드 주니어 개발자를 채용합니다. 자격요건은..', 'java');

insert into recruitment(`recruitment_id`, `company_id`, `recruitment_position`, `recruitment_compensation`, `recruitment_content`, `recruitment_technology`)
 values (6, 1, '프론트엔드 개발자', 500000, '원티드랩에서 프론트엔드 시니어 개발자를 채용합니다. 자격요건은..','javascript');
