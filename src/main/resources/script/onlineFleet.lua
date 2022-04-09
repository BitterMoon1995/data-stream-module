local online_sns
online_sns = redis.call('ZREVRANGE', KEYS[1], 0, -1)

--不支持MGET命令；而且Redis-lua的任何命令的任何参数只能是int或者string
--放弃
--return redis.call('MGET',online_sns)

return redis.call('GET','sf:0X01010006')
