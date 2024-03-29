package de.forsthaus.backend.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PegawaiUtil {
	public static List<Integer> getTanggalBulanSekarang(Date tglAwal) {
		try {
			List<Integer> result = new ArrayList<Integer>();
			Date now = new Date();
			Calendar calAwal = Calendar.getInstance();
			Calendar calAkhir = Calendar.getInstance();

			calAwal.setTime(tglAwal);
			calAkhir.setTime(now);

			int yearAwal = calAwal.get(Calendar.YEAR);
			int yearAkhir = calAkhir.get(Calendar.YEAR);
			int monthAwal = calAwal.get(Calendar.MONTH);
			int monthAkhir = calAkhir.get(Calendar.MONTH);

			int year = monthAkhir >= monthAwal ? yearAkhir - yearAwal
					: (yearAkhir - yearAwal) - 1;
			int month = 0;
			if (year == 0) {
				month = monthAkhir - monthAwal;
			} else {
				month = ((12 - monthAwal) + monthAkhir) % 12;
			}

			result.add(year);
			result.add(month);

			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public static String getRoman(int i){
		String [] romans = {"I","II","III","IV","V","VI","VII","VIII","IX","X"};
		String roman ="";
		if(i>=0 && i<=10){
			roman = romans[i-1];
		}
		return roman;
	}
	
	public static String getRomanToDec(String roman){
		String [] romans = {"I","II","III","IV","V","VI","VII","VIII","IX","X"};
		String dec ="";
		for (int i = 0; i < romans.length; i++) {
			if(romans[i].equals(roman)){
				dec = String.valueOf((i+1));
				break;
			}
		}
		return dec;
	}
	
	public static String getKodeJabatan(String jabatan){
		if(jabatan.equals(ConstantsText.JABATAN_STRUKTURAL)){
			return "1";
		}else if(jabatan.equals(ConstantsText.JABATAN_FKHUSUS)){
			return "2";
		}else if(jabatan.equals(ConstantsText.JABATAN_FUMUM)){
			return "4";
		}
		return "";
	}
	
	public static String convertGolongan(String kodeGol){
		try{
			String [] awal = {"I","II","III","IV"};
			String [] akhir = {"a","b","c","d","e"};
			int iAwal = Integer.parseInt(String.valueOf(kodeGol.charAt(0)));
			int iAkhir = Integer.parseInt(String.valueOf(kodeGol.charAt(1)));
			if((iAwal>=1 && iAwal<=4) && (iAkhir>=1 && iAkhir<=5)){
				return awal[iAwal-1] + "/" + akhir[iAkhir-1];
			}else{
				return "";
			}
		}catch(Exception e){
			return "";
		}
	}
	
	public static String convertEselon(String kodeGol){
		try{
			String [] awal = {"I","II","III","IV"};
			String [] akhir = {"A","B","C","D","E"};
			int iAwal = Integer.parseInt(String.valueOf(kodeGol.charAt(0)));
			int iAkhir = Integer.parseInt(String.valueOf(kodeGol.charAt(1)));
			if((iAwal>=1 && iAwal<=4) && (iAkhir>=1 && iAkhir<=5)){
				return awal[iAwal-1] + "." + akhir[iAkhir-1];
			}else{
				return "";
			}
		}catch(Exception e){
			return "";
		}
	}
	
	public static void main(String[] args) {
		char a = 'a';
		List list = getTanggalBulanSekarang(new Date());
		System.out.println(list.get(0));
		System.out.println(list.get(1));
	}
}
