package com.mysite.sbb;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
//final이 붙은 해당 속성을 필요로 하는 생성자가 롬복에 의해 자동으로 생성된다
//final이 붙으므로 사실상 setter는 무의미 함
@Getter
@Setter
public class HelloLombok {
    private final String hello;
    private final int lombok;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok("헬로",5);
        System.out.println(helloLombok.getHello());
        System.out.println(helloLombok.getLombok());
    }
}
