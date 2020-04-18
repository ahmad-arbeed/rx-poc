package com.warba.rx.poc.rxpoc.controller;

import com.warba.rx.poc.rxpoc.entity.Kyc;
import com.warba.rx.poc.rxpoc.entity.User;
import com.warba.rx.poc.rxpoc.repo.KycRepo;
import com.warba.rx.poc.rxpoc.repo.UserRepository;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cif/opening")
public class CifOpeningController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KycRepo kycRepo;

    @GetMapping("/{civilId}")
    public ResponseEntity<Boolean> checkExistingCivilId(@PathVariable("civilId") String civilId) {

        if (userRepository.existsUserByCivilId(civilId)) {
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.ok(false);
    }

    @PostMapping("/{civilId}")
    public ResponseEntity<Void> openCif(@PathVariable("civilId") String civilId) {

        Observable<Object> userObservable = Observable.create(emitter -> {
            try {
                User user = new User();
                user.setCivilId(civilId);
                user.setName("name-" + System.currentTimeMillis());

                System.out.println(Thread.currentThread().getName());
                User resp = userRepository.save(user);
                emitter.onNext(resp);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io());

        Observable<Object> kycObservable = Observable.create(emitter -> {
            try {
                Kyc kyc = new Kyc();
                kyc.setCivilId(civilId);
                kyc.setSalary(new BigDecimal(10000));

                System.out.println(Thread.currentThread().getName());
                Kyc resp = kycRepo.save(kyc);
                emitter.onNext(resp);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io());

        Observable.merge(userObservable, kycObservable)
                .collect(Collectors.toList())
                .subscribe(resp -> {
                    System.out.println("Response: " + resp);
                    System.out.println(Thread.currentThread().getName());
                }, Throwable::printStackTrace);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        observable
//                .flatMap(c -> Single.create(emitter -> {
//                    try {
//                        Kyc kyc = new Kyc();
//                        kyc.setCivilId(civilId);
//                        kyc.setSalary(new BigDecimal(10000));
//
//                        Kyc resp = kycRepo.save(kyc);
//                        emitter.onSuccess(resp);
//                    } catch (Exception e) {
//                        emitter.onError(e);
//                    }
//                }))
//                .subscribeOn(Schedulers.io())
//                .subscribe(resp -> {
//                    System.out.println("Kyc created successfully id: " + resp);
//                    System.out.println(Thread.currentThread().getName());
//                }, Throwable::printStackTrace);


        // show logs before main threads end

        return ResponseEntity.ok().build();
    }
}
