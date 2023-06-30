package bemlyGPT;

public class Package {
	public Msg msg;
	public int packageType;
	public Exception err;
	public User usr;
	
	public Package() {}
	// 1 发送  POST:3请求成功
	public Package(int packageType, Msg msg) {
		this.packageType = packageType;
		this.msg = msg;
	}
	// 2请求接收
	public Package(int packageType, User usr) {
		this.packageType = packageType;
		this.usr = usr;
	}
	// GET:3请求成功
	public Package(int packageType) {
		this.packageType = packageType;
	}
	// 4请求失败
	public Package(int packageType, Exception err) {
		this.packageType = packageType;
		this.err = err;
	}
}
