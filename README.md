# microservice
a simple maven pro in order to know archaius well
一、项目搭建，测试archaius的热配置功能
1.1项目结构


1.2项目搭建
1.2.1 pom.xml



完整的pom.xml文件如下


1.2.2 ServiceContants


1.2.3 ConsulConfigurationSource
类说明：这个类实现了PolledConfigurationSource接口，作用是轮询配置数据源（key-value、数据库等）获取最新的内容。

Consul consul = Consul.builder().withHostAndPort(HostAndPort.
        fromString("172.20.10.14:8500")).build(); //创建一个consul对象，IP和PORT都是serverAgent的IP和PORT，8500是consul的默认端口
KeyValueClient kvClient = consul.keyValueClient();//创建一个KeyValueClient 对象
Optional<String> kvOpt = kvClient.getValueAsString(keyName);//keyName就是key-value中的key，即service/config/dev
String kvStr = StringUtils.EMPTY;
if (kvOpt.isPresent()){//如果key存在，取value值
    kvStr = kvOpt.get(); //结果kvStr=”animal.name=dog”
}
Properties properties = new Properties();
properties.load(new StringReader(kvStr)); //将取出的值转化为Properties
完整ConsulConfigurationSource.java如下：


1.2.4 ConsulPropertySourceInitializer
类说明：这个类实现了ApplicationContextInitializer<ConfigurableApplicationContext>接口，重写了initialize方法，其目的是为了指定initialize方法在spring启动过程的某个恰当时刻去执行。

System.setProperty("archaius.fixDelayPollingScheduler.delayMills","10000");//默认是60000，即每隔60秒拉取一次配置信息，这里设置的10000，也就是说每隔10s拉取一次配置信息。
String keyName = "service/"+ ServcieContants.serviceName+"/"+ ServcieContants.serviceTag;//组装key-value中的key值字符串
PolledConfigurationSource configSource = new ConsulConfigurationSource(keyName);//轮询配置源
AbstractPollingScheduler scheduler = new FixedDelayPollingScheduler();//此类负责调度配置源的定期轮询，并将轮询结果应用于配置。
DynamicConfiguration configuration = new DynamicConfiguration(configSource, scheduler);//配置中的属性值将在运行时动态更改，如果配置源中的值更改。

完整的ConsulPropertySourceInitializer如下：

1.2.5 TestController
   	        DynamicStringProperty dsp = DynamicPropertyFactory.getInstance()
                .getStringProperty("animal.name", "cat"); //获取动态参数animal.name

完整的TestController如下：


1.2.6 Application
完整的Application如下：


1.3项目运行
在浏览器中输入http://localhost:8080/archaius/test/getAnimalName
返回结果为dog，如果手动修改service/config/dev中animal.name的值，则10秒后会返回最新的值。
Key-value的访问路径http://172.20.10.14:8500/ui/dc1/kv/service/

