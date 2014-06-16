package weiyuan.searchviewtest;

public class Device implements Comparable<Device>{
	
	private String name;
	private Integer faulty;
	
	
	public Device(String name, Integer faulty){
		this.name = name;
		this.faulty = faulty;
		
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getFaulty() {
		return faulty;
	}


	public void setFaulty(Integer faulty) {
		this.faulty = faulty;
	}


	@Override
	public int compareTo(Device d) {
		int nameComp = name.compareTo(d.getName());
		return nameComp == 0? faulty.compareTo(d.getFaulty()) : nameComp ;
	}

}
