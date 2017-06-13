##File
* 一个Fiel类的对象，表示磁盘行的文件或者目录
* File仅仅表示文件本身，无法改变文件内容
* 获取指定文件类型的数组（比如获取以“.java”结尾的文件）
    * `String[] str = file.list(FileFilter filter)`
* 递归，就是方法调用自身，对于递归来说，一定有一个出口让递归结束，只有这样才能保证不出现死循环
* 递归案例
    * 斐波那契梳理，这个数列从第三项开始，每一项等于前两项之和
    * 例如：1，1，2，3，5，8，13，21...
    
        ```java
        /**
        * 计算斐波那契数列中第n个数的值
        */
        public int compute(int n){
            if(n == 1 || n == 2){
                return 1;
            } {
                retunr compute(n - 1) + cumpute(n - 2);
            }
        }
        ```
    
    * 结算阶乘 n! = n * (n-1) * (n-2)...

        ```java
        /**
        *计算n的阶乘
        */
        public void compute(int n){
            if(n == 1){
                return 1;
            }
            return n * compute(n - 1);
        }
        ```
    
    * 删除指定目录（目录中包含文件或者文件夹，文件夹可能有子目录）

        ```java
            /**
            *删除file目录及其所有子目录
            */
            public void deleteAll(File file){
                if(file.isFile || file.list().length==0){
                    file.delete;
                }else{
                    File[] files = file.listFiles();
                    for(File f : files){
                        deleteAll(f);
                    }
                    //删除最外层目录
                    file.delete();
                }
            }
        ```
        
* ###输入流输出流
    * 输入输出是相对于程序来说的，从外面流入到程序（内存）叫做输入，从程序流出到磁盘（控制台等）叫做输出
    * 程序在使用数据是扮演两个角色：一个是**源**一个是**目的地**。若程序是数据的源，即数据的提供者，这个数据流对于程序来说就是一个**输出数据流**（数据从程序流出）；若程序是数据的终点，这个数据流对程序而言就是一个**输入数据流**（数据从程序外流向程序）
    * 在java.io中提供了60多个类，从**功能**上分为**输入流和输出流**从**流结构**上分为**字节流和字符流**
        * 字节流：以字节为处理单位或称面向字节
        * 字符流：以字符为处理单位或称面向字符
    * 字节流的输入输出的基础是**InputStream**和**OutputStream**这两个**抽象类**字节流的输入输出流这俩个类的子类来实现，字符流是Java1.1版本后新增加的对以字符为单位的输入输出流的操作，字符流的输入输出基础是**Reader**和**Writer**两个抽象类
* ###InputStream和OutputStream
    * 读取abc.txt文件中的数据并输入到控制台

        ```java
            FileInputStream is = new FileInputStream(new File("abc.txt"));
            //定义缓冲
            byte[] buffer = new byte[200];
            //实际读取的长度
            int len;
            while ((len = is.read(buffer,0,buffer.length)) != -1){
                String str = new String(buffer,0,len);
                System.out.print(str);
            }
            is.close();
        ```
        
    * 将字符串写入（输出）到文件中

        ```java
        FileOutputStream os = new FileOutputStream("def.txt");
        String str = "liusilong";
        byte[] buffer= str.getBytes();
        os.write(buffer);
        os.close();
        ```
    
* ###节点流和过滤流
    * 节点流：直接操作数据的流
    * 过滤流：用于包装节点流，过滤流的主要特点是在输入输出数据的同时能对所传输的数据做指定类型或者格式的转换
    * **InputStream**的过滤流和节点流
        * 节点流
            * FileInputStream
            * ByteArrayInputStream
            * ObjectInputStream
            * PipedInputStream
            * SequenceInputStream
            * StringBufferInputStream
        * 过滤流
            * FilterInputStream
                * DataInputStream
                * BufferedInputStream
                * LineNumberInputStream
                * PushbackInputStream
    * **OutPutStream**的过滤流和节点流
        * 节点流
            * FileOutputStream
            * ByteArrayOutputStream
            * ObjectOutputStream
            * PipedOutputStream
        * 过滤流
            * FileOutputStream
                * DataOutputStream
                * BufferedOutputStream
                * PrintStream
* ###BufferedOutputStream(缓冲输出流)
	* 缓冲是将文件写到内存中的缓冲区，不会自动将文件写到磁盘，需要flush
	* close的时候会自动的flush
	* 案例：将“liusilong”写入到1.txt文件中
	
		```java
		OutputStream os = new FileOutputStream("1.txt");
		BufferedOutputStream bos = new BufferedOutputStream(os);
		bos.write("liusilong".getBytes());
		bos.close();
		```
	* FileOutputStream("1.txt")直接传文件名，文件不存在会自动新建，例子中是在当前目录（项目所在目录）中新建1.txt文件
	* **缓冲流最后必须要关闭掉，这个才能将缓冲区的数据flush出来**

* ###ByteArrayInputStream和ByteArrayOutputStream
	* **ByteArrayInputStream**是把数组当成源的输入流
		* ByteArrayInputStream（byte array[]）
		* ByteArrayInputStream（byte array[],int start,int length）
	* 这里，array是输入源；start是从哪里读；length是读多长
	* **ByteArrayOutputStream**
    	* ByteArrayOutputStream();
    	* ByteArrayOutputStream(int size);
	* 案例

	   ```java
	       String str = "liusilong";
	       //将字符串转换为字节数组
	       byte[] b = str.getBytes();
	       ByteArrayInputStream bis = new ByteArrayInputStream(b);
	       int len;
	       //一个个的读取
	       while((len = bis.read())！=-1){
	           System.out.print((char)len);
	       }
	       
	       ByteArrayOutputStream bos = new ByteArrayOutputStream();
	       //将b写入到bos中
	       bos.write(b);
	       //将流中的数据转成字节数组
	       byte[] result = bos.toByteArray();
	       for(int i = 0; i<result.length; i++){
	           System.out.print((char)result[i]);
	       }
	       //将bos中的数据直接写入到aaa.txt文件中
	       FileOutputStream fos = new FileOutputStream("aaa.txt");
	       bos.writeTo(fos);
	       bos.close();
	       fos.close();
	   ```
* ###DataInputStream和DataOutputStream
    * 写入基本数据类型

        ```java
        //写入
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("data.txt")));
        byte b = 3;
        int i = 2;
        char c = 'a';
        float f = 2.2f;
        //写入data.txt文件中
        dos.writeByte(b);
        dos.writeInt(i);
        dos.writeChar(c);
        dos.writeFloat(f);
        
        dos.close();
        
        //取出
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(data.txt)));
        //注意：这里读和写的顺序要保持一致
        System.out.println(dis.readByte());
        System.out.println(dis.readInt());
        System.out.println(dis.readChar());
        System.out.println(dis.readFloat());
        dis.close();
        ```
    * 控制台输出

        ```java
        3
        2
        a
        2.2
        ```
        
****
* **JavaIO 中的设计模式:装饰模式(Decorator)**
    * 装饰模式以对客户端透明的方式扩展**对象**的功能，是继承关系的一个替代方案
    * **装饰模式可以在不创早更多子类的情况下将对象的功能加以扩展**
    * 装饰模式的角色
        * **抽象构建角色**:给出一个抽象接口，以规范准备接受附加责任的对象（类似于IO中的**InputStream**）
        * **具体构建角色**:定义一个将要接受附加责任的类（类似于IO中的**FileInputStream**）
        * **装饰角色**:**持有一个构建对象的引用**，并定义一个与抽象构建接口一致的接口（类似于IO中的**FilterInputStream**）
        * **具体装饰角色**:负责给构建对象“贴上”附加的责任（类似于IO中的**BufferedInputStream**, FilterInputStream的子类）
    * 装饰模式的特点
        * 装饰对象和真实对象有相同的接口。这样客户端对象就可以以和真实对象相同的方式和装饰对向交互。
        * **装饰对象包含一个真实对象的引用**
        * 装饰对象接受所有来自客户端的请求。他把这些请求转发给真实的对象
        * 装饰对象可以再转发这些请求以前或以后增加一些附加功能。这样就确保了在运行时，不用修改给定对象的结构就可以在外部增加附加的功能。在面向对象的设计中，通常是通过集成来实现对给定类的功能扩展
    * 装饰模式**VS**继承
        * 装饰模式：
            * 用来扩展特定对象的功能
            * 不需要子类
            * 动态
            * 运行时分配职责
            * 防止由于子类而导致的复杂和混乱
            * 更多的灵活性
            * 对于一个给定的对象，同时可能有不同的装饰对象，客户端可以通过他的需要选择合适的装饰对象发送消息
        * 继承：
            * 用来扩展一类对象的功能
            * 需要子类
            * 静态
            * 编译时分派职责
            * 导致很多子类产生
            * 缺乏灵活性
    * **装饰模式案例**：
        * 抽象构建角色（InputStream）
    
            ```java
            /**
             * 抽象构建角色（InputStream）
             * @author liusilong
             *
             */
            public interface Component {
            	void doString();
            }
            ```
            
        * 具体构建角色(FileInputStream)
    
            ```java
            /**
             * 具体构建角色（FileInputStream）
             * @author liusilong
             *
             */
            public class ConcreteComponent implements Component {
            
            	@Override
            	public void doString() {
            		System.out.println("功能A");
            	}
            }
            ```
            
        * 装饰角色(FilterInputStream)
    
            ```java
            /**
             * 装饰角色（FilterInputStream）
             * 1.实现抽象构建角色
             * 2.持有抽象构建角色引用
             * @author liusilong
             *
             */
            public class Decorator implements Component {
            	Component component;
            
            	public Decorator(Component component) {
            		this.component = component;
            	}
            
            	@Override
            	public void doString() {
            		component.doString();
            	}
        
            }
            ```
            
        * 具体装饰角色A(DataInputStream)
    
            ```java
            /**
             * 具体装饰角色（DataInputStream）
             * @author liusilong
             *
             */
            public class ConcreteDecorator1 extends Decorator {
            
            	public ConcreteDecorator1(Component component) {
            		super(component);
            	}
            
            	@Override
            	public void doString() {
            		super.doString();
            		System.out.println("功能B");
            	}
        
            }  
            ```
            
        * 具体装饰角色B(BufferedInputStream)
    
            ```java
            /**
             * 具体装饰角色（BufferedInputStream）
             * @author liusilong
             *
             */
            public class ConcreteDecorator2 extends Decorator {
            
            	public ConcreteDecorator2(Component component) {
            		super(component);
            	}
            
            	@Override
            	public void doString() {
            		super.doString();
            		System.out.println("功能C");
            	}
        
            }
            ```
            
        * 客户端调用
    
            ```java
        	public static void main(String[] args) {
            //		节点流
            		Component component = new ConcreteComponent();
            //		过滤流
            		Component component2 = new ConcreteDecorator1(component);
            //		过滤流
            		Component component3 = new ConcreteDecorator2(component2);
            	
            		component3.doString();
        		
        	}
            ```
            
        * 控制台输出
    
            ```java 
            功能A
            功能B
            功能C
            ```
            
* ###字符流
    * 字符流对应两个抽象类 **Reader和Writer**
    * **Reader**
        * BufferedReader
        * CharArrayReader
        * RilterReader
        * InputStreamReader
        * PipedReader
        * StringReader
    * **Writer**
        * BufferedWriter
        * CharArrayWriter
        * FilterWriter
        * OutputStreamWriter
        * PipedWriter
        * PrintWriter
        * StringWriter
    
	
