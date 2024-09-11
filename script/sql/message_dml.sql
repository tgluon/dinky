insert  into crawler.matrix_message_user
(platform, aweme_id, comment_id, user_id,user_url,aweme_url,nickname,ip_location,content,comment_create_time,keyword,status)
select platform,
       aweme_id,
       comment_id,
       user_id,
       user_url,
       aweme_url,
       nickname,
       ip_location,
       content,
       comment_create_time,
       keyword,
       status
from(
        select dac.platform,
               dac.aweme_id,
               dac.comment_id,
               dac.user_id,
               dac.user_url,
               dac.aweme_url,
               dac.nickname,
               dac.ip_location,
               dac.content,
               dac.comment_create_time,
               dac.keyword,
               0 as status,
               mmu.platform as mmu_platform
        from dwd.dwd_aweme_comment dac
                 left join crawler.matrix_message_user mmu
                           on  dac.platform=mmu.platform
                               and dac.aweme_id=mmu.aweme_id
                               and dac.comment_id=mmu.comment_id
    )rs
where mmu_platform is null