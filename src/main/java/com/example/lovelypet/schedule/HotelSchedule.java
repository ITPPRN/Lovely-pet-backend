package com.example.lovelypet.schedule;

import com.example.lovelypet.business.HotelBusiness;
import com.example.lovelypet.business.UserBusiness;
import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.User;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.service.HotelService;
import com.example.lovelypet.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

@Service
@Log4j2
public class HotelSchedule {

    private final HotelBusiness hotelBusiness;

    private final HotelService hotelService;


    public HotelSchedule( HotelBusiness hotelBusiness, HotelService hotelService) {
        this.hotelBusiness = hotelBusiness;
        this.hotelService = hotelService;
    }

    //1 => second
    //2 => minute
    //3 => hour
    //4 => day
    //5 => year
//
//    /**
//     * Every minute (UTC Time)
//     */
//    @Scheduled(cron = "0 * * * * *")
//    public void testEveryMinute() {
//        log.info("Hello,What's up?");
//    }
//
//    /**
//     * Every day at 00.00 (Thai Time)
//     */
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Bangkok")
    public void deleteHotelAccountEveryMidNight() throws BaseException {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        Date dateDelete = calendar.getTime();
        Optional<Hotel> opt = hotelService.findByDateDelete(dateDelete);
        if (opt.isEmpty()) {
            return;
        }
        hotelBusiness.deleteAccount(opt.get().getId());
    }

}
