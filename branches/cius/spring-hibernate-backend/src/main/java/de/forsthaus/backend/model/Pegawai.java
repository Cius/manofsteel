package de.forsthaus.backend.model;

import java.util.Date;

public class Pegawai {
	private String nip;
	private String nama;
	private String tmpLahir;
	private Date tglLahir;
	private String kotaLahir;
	private String jenisKelamin;
	
	private MasterGabungan agama;
	private MasterGabungan statusKepeg;
	private MasterGabungan jenisKepeg;
	private MasterGabungan kedudukanPeg;
	private String statusKaw;
	
	private String golDarah;
	private String alamat;
	private String rtrw;
	private String telp;
	private String kodePos;	
	private Provinsi prov;
	private Kota kota;
	private Kecamatan kec;
	private Kelurahan kel;
	
	private String noKarPeg;
	private String noAskes;
	private String noTaspen;
	private String noKaris;
	private String npwp;
	private String noKTP;
	private String nipLama;
	
	private Cpns detAngkatC;
	private Pns detAngkat;
	private KgbPangkat pangkat;
	private MasterJabatan jabatan;
}
