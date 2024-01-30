if redis.call("get", KEYS[1]) == false then
    redis.call("set", KEYS[1], 0)
end
redis.call("incr", KEYS[1])
return redis.call("get", KEYS[1])