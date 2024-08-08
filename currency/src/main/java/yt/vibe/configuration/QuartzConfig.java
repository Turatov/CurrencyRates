//package yt.vibe.configuration;
//
//import org.quartz.spi.JobFactory;
//import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//
//@Configuration
//public class QuartzConfig {
//
//    @Bean
//    public JobFactory jobFactory(AutowireCapableBeanFactory beanFactory) {
//        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
//        jobFactory.createJobInstance(beanFactory);
//        return jobFactory;
//    }
//a
//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory) {
//        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//        factory.setJobFactory(jobFactory);
//        return factory;
//    }
//}
