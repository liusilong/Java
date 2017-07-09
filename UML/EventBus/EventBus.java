EventBus使用：
1.在订阅事件的Activity或者Fragment中需要注册和反注册
	onCreate()-->EventBus.getDefault().register(this);
	onDestory()-->EventBus.getDefault().unregister(this);
2.源码：
	1.首先看看EventBus.getDefault()源码如下，就是通过单例获取一个EventBus的实例
		static volatile EventBus defaultInstance;
		public static EventBus getDefault() {
			if (defaultInstance == null) {
				synchronized (EventBus.class) {
					if (defaultInstance == null) {
						defaultInstance = new EventBus();
					}
				}
			}
			return defaultInstance;
		}
	2.再来看看register(this)
		public void register(Object subscriber) {
        Class<?> subscriberClass = subscriber.getClass();
        List<SubscriberMethod> subscriberMethods = subscriberMethodFinder.findSubscriberMethods(subscriberClass);
        synchronized (this) {
            for (SubscriberMethod subscriberMethod : subscriberMethods) {
                subscribe(subscriber, subscriberMethod);
            }
        }
	}
	注释：
	subscriber是传递进来的对象Activity/Fragment
	subscriberClass是Activity/Fragment的class对象
	subscriberMethodFinder.findSubscriberMethods是在查找当前Activity/Fragment中的订阅方法
	
	3.SubscriberMethodFinder#findSubscriberMethods
	
	//缓存订阅方法的Map集合
	private static final Map<Class<?>, List<SubscriberMethod>> METHOD_CACHE = new ConcurrentHashMap<>();
	//传递进来的Activity/Fragment
	List<SubscriberMethod> findSubscriberMethods(Class<?> subscriberClass) {
		//从缓存中取出subscriberMethods
        List<SubscriberMethod> subscriberMethods = METHOD_CACHE.get(subscriberClass);
        if (subscriberMethods != null) {
            return subscriberMethods;
        }
		//ignoreGeneratedIndex默认为false
        if (ignoreGeneratedIndex) {
            subscriberMethods = findUsingReflection(subscriberClass);
        } else {
			//所以默认到这来
            subscriberMethods = findUsingInfo(subscriberClass);
        }
        if (subscriberMethods.isEmpty()) {
            throw new EventBusException("Subscriber " + subscriberClass
                    + " and its super classes have no public methods with the @Subscribe annotation");
        } else {
            METHOD_CACHE.put(subscriberClass, subscriberMethods);
            return subscriberMethods;
        }
    }
	
	SubscribeMethodFinder#findUsingReflectionInSingleClass
		//利用反射获取Activity/Fragment中的方法数组
		methods = findState.clazz.getDeclaredMethods();
		//便利方法数组
		for (Method method : methods) {
			//获取方法的修饰符 public、private、static。。。
				int modifiers = method.getModifiers();
				//判断方法的修饰符
				if ((modifiers & Modifier.PUBLIC) != 0 && (modifiers & MODIFIERS_IGNORE) == 0) {
					//获取方法的参数列表
					Class<?>[] parameterTypes = method.getParameterTypes();
					//如果方法的参数个数为1
					if (parameterTypes.length == 1) {
						//获取方法上的注解
						Subscribe subscribeAnnotation = method.getAnnotation(Subscribe.class);
						//判断方法上有没有这个注解 @Subscribe
						if (subscribeAnnotation != null) {
							//获取该方法上的第一个参数的class对象
							Class<?> eventType = parameterTypes[0];
							//checkAdd方法在首次添加的时候会返回true
							if (findState.checkAdd(method, eventType)) {
								//获取执行该方法的线程的类型比如：主线程，后台线程。。。
								ThreadMode threadMode = subscribeAnnotation.threadMode();
								findState.subscriberMethods.add(new SubscriberMethod(method, eventType, threadMode,
										subscribeAnnotation.priority(), subscribeAnnotation.sticky()));
							}
						}
					} else if (strictMethodVerification && method.isAnnotationPresent(Subscribe.class)) {
						String methodName = method.getDeclaringClass().getName() + "." + method.getName();
						throw new EventBusException("@Subscribe method " + methodName +
								"must have exactly 1 parameter but has " + parameterTypes.length);
					}
				} else if (strictMethodVerification && method.isAnnotationPresent(Subscribe.class)) {
					String methodName = method.getDeclaringClass().getName() + "." + method.getName();
					throw new EventBusException(methodName +
							" is a illegal @Subscribe method: must be public, non-static, and non-abstract");
				}
			}
		
	