package xyz.arifguler.strappv13;

public class FoursquareVenue {

	private String name;
	private String city;
	private double lat;
	private double lng;
	private String category;
	private String address;


	public FoursquareVenue() {
		this.name = "";
		this.address = "";
		this.city = "";
		this.setCategory("");
		this.lng=0;
		this.lat=0;

	}

	public String getCity() {
		if (city.length() > 0) {
			return city;
		}
		return city;
	}

	public void setCity(String city) {
		if (city != null) {
			this.city = city.replaceAll("\\(", "").replaceAll("\\)", "");
			;
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public  void setAddress(String address){this.address=address;}
	public String getAddress(){return address;}


	public void setLat(Double lat){this.lat=lat;}
	public Double getLat(){return lat;}


	public void setLng(Double lng){this.lng=lng;}
	public Double getLng(){return lng;}


}
