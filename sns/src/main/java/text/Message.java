package text;

public class Message {

	int mid;
	String uid;
	String msg;
	int favcount;
	int replycount;
	String date;

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getFavcount() {
		return favcount;
	}

	public void setFavcount(int favcont) {
		this.favcount = favcont;
	}

	public int getReplycount() {
		return replycount;
	}

	public void setReplycount(int replycont) {
		this.replycount = replycont;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String datetime) {
		this.date = datetime;
	}

}
