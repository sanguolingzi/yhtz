-- 以下脚本已经执行 201809151356 begin
ALTER TABLE busi_sys_sysproperties
ADD CONSTRAINT uk_name UNIQUE (p_name);
-- 以上脚本已经执行 201809151356 end