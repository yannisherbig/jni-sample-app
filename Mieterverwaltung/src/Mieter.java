
public class Mieter {
	private long mieterID;
	private String name;
	private String vorname;
	private int alter;
	private int telefonnummer;
	private long mietobjektID;
	
	public Mieter(long mieterID, String name, String vorname, int alter, int telefonnummer, long mietobjektID) {
		super();
		this.mieterID = mieterID;
		this.name = name;
		this.vorname = vorname;
		this.alter = alter;
		this.telefonnummer = telefonnummer;
		this.mietobjektID = mietobjektID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public int getAlter() {
		return alter;
	}
	public void setAlter(int alter) {
		this.alter = alter;
	}
	public int getTelefonnummer() {
		return telefonnummer;
	}
	public void setTelefonnummer(int telefonnummer) {
		this.telefonnummer = telefonnummer;
	}
	public long getMietobjektID() {
		return mietobjektID;
	}
	public void setMietobjektID(long mietobjektID) {
		this.mietobjektID = mietobjektID;
	}
	public long getMieterID() {
		return mieterID;
	}
}
