package com.zhao.aviationsystem;

import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * 航班类
 * @author zhao
 * @日期 2015-11-8 17:24
 * 用newInstance()方法来创建实例
 */
public class Flight implements Comparable<Flight>,Serializable {

	private static final long serialVersionUID = 1L;
	public  static TreeMap<String,Flight> allFlight;//所有的航班
	public static TreeMap<Integer,User> allUser;//所有订该航班的乘客
	private String id;//航班的班次名称 
	private int amount;//该趟航班的荷载人数
	private int orderCount;//以被订的数量
	private String origin;//航班的起点
	private String destination;//航班的目的地
	private Time time;//航班时间
	static{//初始化allFlight和allUser
		allFlight = new TreeMap<String, Flight>();
	}
	/**
	 * 构造一个空的航班
	 */
	private Flight(String id,int amount,String origin,String destination,Time time) {
		this.id=id;
		this.amount=amount;
		this.origin=origin;
		this.destination=destination;
		this.time=time;
		orderCount=0;
		allUser=new TreeMap<Integer,User>();
	}
	
	/**
	 * 新建一个航班实例<br/>
	 * 检测是否含有重复的航班名称
	 * @param id 航班班次
	 * @param amount 航班核定载客数量
	 * @return 一个新的航班实例
	 */
	public static Flight newInstance(String id,int amount,String origin,String destination,Time time){
		if(allFlight.containsKey(id)){
			System.err.println("航班名称重复 请重试!");
			return null;
		}
		Flight flight= new Flight(id,amount,origin,destination,time);
		Flight.allFlight.put(id, flight);
		return flight;
	}
	/**
	 * 以时间来对航班
	 */
	@Override
	public int compareTo(Flight o) {
		
		return this.time.compareTo(o.getTime());
	}
	/**
	 * 添加乘客 
	 * 添加成功返回true 
	 * 失败返回false
	 * @param user
	 */
	public boolean addUser(User user){
		/**
		 * 检测是否有重复的乘客
		 */
		if ((!Flight.allUser.containsKey(user.getId()))&&(orderCount<amount)) {
			allUser.put(user.getId(), user);
			orderCount++;
			return true;
		}else{
			System.err.println("该乘客已经订该趟航班!");
			return false;
		}
	}
	/**
	 * 减少乘客
	 */
	public void deleteUser(User user){
		/**
		 * 检查是否有该乘客
		 */
		if(User.allUser.containsKey(user.getId())){
			allUser.remove(user.getId());
			orderCount--;
		}
	}
	/**
	 * 各种get 和set
	 * 起飞时间 可以修改
	 * @return
	 */
	
	public Time getTime() {
		return time;
	}
	
	public void setTime(Time time) {
		this.time = time;
	}
	
	public String getId() {
		return id;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public int getOrderCount() {
		return orderCount;
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public String getDestination() {
		return destination;
	}
	/**
	 * 对allFlight操作
	 * 元素是否存在在列表中
	 */
	public static  boolean  containsKey(String id){
		return allFlight.containsKey(id);
	}
	/**
	 * 输入航班班次得到该航班实例
	 */
	public static Flight getFlight(String id){
		if(containsKey(id))
			return allFlight.get(id);
		System.err.println("无该趟航班");
		return null;
	}
	/**
	 * 显示该id航班
	 */
	public static void disFlight(String id){
		Flight flight=getFlight(id);
		if(flight!=null){
			System.out.println(flight);
		}
	}
	/**
	 * 显示所有航班信息
	 */
	public static void disAllFlight(){
		Iterator<String> it=allFlight.keySet().iterator();
		while (it.hasNext()) {
			System.out.println(allFlight.get(it.next()));
		}
	}
	/**
	 * 查询某条航线上的航班
	 */
	public static void disFlightLine(String origin,String destination){
		Iterator<String> it=allFlight.keySet().iterator();
		int count=0;
		while(it.hasNext()){
			Flight flight=allFlight.get(it.next());
			if(flight.getOrigin().equals(origin)&&flight.getDestination().equals(destination)){
				System.out.println(flight);
				count=1;
			}
		}
		if(count==0){
			System.out.println("抱歉!无此航线的航班.");
		}
	}
	/**
	 * 删除一趟航班
	 * @param id
	 * @return
	 */
	public static boolean  deleteFlight(String id){
		if(allFlight.containsKey(id)){
			allFlight.remove(id);
			System.out.println("删除航班成功!");
			return true;
		}
		System.err.println("错误:无此航班!");
		return false;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "航班:"+this.id+"        起点:"+this.origin+"       目的地:"
				+this.destination+"       时间:"+time+"      剩余票量:"+(this.amount-this.orderCount);
	}
	
}
