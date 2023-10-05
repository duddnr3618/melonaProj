//package com.fundguide.melona.ywstest;
//
//import okhttp3.OkHttpClient;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * 라이브러리 설정 매개변수를 설정할 수 있게 해줍니다.
// *
// * @since 1.0.0
// * @author Sylvester Sefa-Yeboah
// */
//public class Config {
//
//    public static final String BASE_URL = "https://www.alphavantage.co/query?";
//
//    private final String key;
//    private final int timeOut;
//    private final OkHttpClient httpClient;
//
//    private Config(Builder builder) {
//        this.key = builder.key;
//        this.timeOut = builder.timeOut;
//        this.httpClient = builder.httpClient == null ? defaultClient(builder.timeOut): builder.httpClient;
//    }
//
//    public int getTimeOut() {
//        return timeOut;
//    }
//
//    public String getKey() {
//        return key;
//    }
//
//    public OkHttpClient getOkHttpClient(){
//        return this.httpClient;
//    }
//
//    /**
//     * 빌더 인스턴스를 가져옵니다.
//     *
//     * @return {@link Builder}
//     */
//    public static Builder builder(){
//        return new Builder();
//    }
//
//    /**
//     * 라이브러리를 위한 기본 HTTP 클라이언트를 설정합니다.
//     *
//     * @param timeOut 연결 타임아웃
//     * @return 데이터를 가져오기 위한 기본 HTTP 클라이언트
//     */
//    private OkHttpClient defaultClient(int timeOut){
//        return new OkHttpClient.Builder()
//                .connectTimeout(timeOut, TimeUnit.SECONDS)
//                .build();
//    }
//
//    /**
//     * 설정이 null이 아닌지 및 API 키가 있는지 확인합니다.
//     *
//     * @since 1.4.0
//     * @param config 설정 인스턴스
//     * 설정 인스턴스가 null이거나 키가 비어 있는지 확인합니다.
//     */
//    public static void checkNotNullOrKeyEmpty(Config config) {
//        if (config == null) throw new AlphaVantageException("Config not set");
//        if (config.getKey() == null) throw new AlphaVantageException("API Key not set");
//    }
//
//    public static class Builder {
//
//        private String key;
//        private int timeOut;
//        private OkHttpClient httpClient;
//
//        public Builder key(String key){
//            this.key = key;
//            return this;
//        }
//
//        public Builder timeOut(int timeOut){
//            this.timeOut = timeOut;
//            return this;
//        }
//
//        public Builder httpClient(OkHttpClient httpClient){
//            this.httpClient = httpClient;
//            return this;
//        }
//
//        public Config build(){
//            return new Config(this);
//        }
//    }
//}