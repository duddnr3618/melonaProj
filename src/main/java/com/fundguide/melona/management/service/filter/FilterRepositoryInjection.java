package com.fundguide.melona.management.service.filter;

/*
import lombok.RequiredArgsConstructor;
import org.apache.beam.vendor.bytebuddy.v1_10_8.net.bytebuddy.ByteBuddy;
import org.apache.beam.vendor.bytebuddy.v1_10_8.net.bytebuddy.dynamic.scaffold.InstrumentedType;
import org.apache.beam.vendor.bytebuddy.v1_10_8.net.bytebuddy.implementation.Implementation;
import org.apache.beam.vendor.bytebuddy.v1_10_8.net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import org.apache.beam.vendor.bytebuddy.v1_10_8.net.bytebuddy.matcher.ElementMatchers;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.repository.Repository;

import java.io.IOException;
*/


/**@deprecated
 * 레파지토리를 넘기면 자동으로 필터링 페이지 값을 반환하는 클래스
 * 구현중 사용하지말것 */
/*
@RequiredArgsConstructor
public class FilterRepositoryInjection {

    private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();



    public void addFilterMethod (Repository<?, ?> repository) throws IOException, ClassNotFoundException {

        Class<?> targetRepository = repository.getClass();

        Class<?> findRepositoryClass = new ByteBuddy()
                .subclass(targetRepository)
                .name("$Dynamic")
                .method(ElementMatchers.named("filterViewBoard"))
                .intercept(
                        new Implementation() {
                            @Override
                            public ByteCodeAppender appender(Target target) {
                                target = (Target) repository;
                                return null;
                            }

                            @Override
                            public InstrumentedType prepare(InstrumentedType instrumentedType) {
                                return null;
                            }
                        }
                )
                .make()
                .load(FilterRepositoryInjection.class.getClassLoader())
                .getLoaded()
                ;

        context.close();
    }
}
*/