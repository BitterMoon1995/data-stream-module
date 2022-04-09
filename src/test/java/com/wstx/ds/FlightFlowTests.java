package com.wstx.ds;

import com.wstx.ds.cache.SFCache;
import com.wstx.ds.common.utils.BeanUtils;
import com.wstx.ds.db.SyncFrameMapper;
import com.wstx.ds.model.dto.SfDto;
import com.wstx.ds.service.SyncFrameService;
import com.wstx.ds.sf.msg.SyncFrame;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import javax.jws.soap.SOAPBinding;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class FlightFlowTests {

    @Autowired
    SyncFrameMapper sfMapper;

    @Autowired
    RedisTemplate<String,Object> redis;

    @Autowired
    SFCache SFCache;

    @Autowired
    SyncFrameService syncFrameService;

    @Test
    void dalianjianchu() {
        long timestamp = System.currentTimeMillis();
        SyncFrame syncFrame = new SyncFrame("01131723",timestamp,"0X01010006",
                "0x01010001", "0x0101","0x01010001",100,
                "0xFF","0xFF",34.56485,104.26587,
                490.5,50.5f,60.5f,124);

        System.out.println(sfMapper);
//        sfMapper.insert(syncFrame);
    }

    @Test
    public void beanUtilsTest() {
        SyncFrameMapper sfMapper = BeanUtils.getBean(SyncFrameMapper.class);
        System.out.println(sfMapper);
    }

    @Test
    public void testRedis() {
        System.out.println(redis.hasKey("nigger"));
    }

    @Test
    public void test() {
        SFCache.storeSf(getSf("0x01010006"));
    }

    public SyncFrame getSf(String sn){
        return new SyncFrame("01131723",System.currentTimeMillis(),
                sn, "0x01010001", "0x0101",
                "0x01010001",100, "0xFF","0xFF",104.746593
                ,31.484552, 490.5,
                50.5f,60.5f,124);
    }

    //生成在线无人机数据
    /*
    教训之：经纬度小数点后第五位担怕只有几米......
     */
    @Test
    public void genOnlineVD() throws InterruptedException {

        int n = 11000;
        int scoreIncr = 0;
        BigDecimal increase = new BigDecimal("0.01");
        for (int i = 0; i < 5; i++) {
            String vsn = "0x010" + n;
            SyncFrame sf = getSf(vsn);
            BigDecimal latitude = BigDecimal.valueOf(sf.getLatitude());
            BigDecimal longitude = BigDecimal.valueOf(sf.getLongitude());
            sf.setLatitude(latitude.add(increase).doubleValue());
            sf.setLongitude(longitude.subtract(increase).doubleValue());

            SFCache.storeSf(sf);
            redis.opsForZSet().add(SFCache.onlineFleet,vsn,System.currentTimeMillis() / 1000 + scoreIncr);
            n++;
            scoreIncr++;
            increase = increase.add(new BigDecimal("0.01"));
        }
    }
    @Test
    public void genOfflineVD() throws InterruptedException {

        int n = 12000;
        BigDecimal increase = new BigDecimal("0.01");
        for (int i = 0; i < 4; i++) {
            String vsn = "0x010" + n;
            SyncFrame sf = getSf(vsn);
            BigDecimal latitude = BigDecimal.valueOf(sf.getLatitude());
            BigDecimal longitude = BigDecimal.valueOf(sf.getLongitude());
            sf.setLatitude(latitude.subtract(increase).doubleValue());
            sf.setLongitude(longitude.subtract(increase).doubleValue());

            SFCache.storeSf(sf);
            redis.opsForSet().add(SFCache.offlineFleet,vsn);
            n += 1;
            increase = increase.add(new BigDecimal("0.01"));
        }
    }

    @Test
    public void useLuaGetOnlineFleet(){
        List<String> keys = Collections.singletonList("fleet:online");
        DefaultRedisScript<List> script = new DefaultRedisScript<>();
        ResourceScriptSource source =
                new ResourceScriptSource(new ClassPathResource("script/onlineFleet.lua"));
        script.setScriptSource(source);
        script.setResultType(List.class);
        List resList = redis.execute(script, keys);
        System.out.println(resList);
    }

    @Test
    public void testQuerySf() {
        SyncFrame syncFrame = SFCache.querySf("0x01011000");
        System.out.println(syncFrame);
    }
}
