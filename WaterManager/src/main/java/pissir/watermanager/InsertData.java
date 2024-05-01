package pissir.watermanager;

import pissir.watermanager.model.item.Misura;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class InsertData {
	private static final DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public static void main(String[] args) throws Exception {
		
		InsertData data = new InsertData();
		
		Misura misura = new Misura(83.5,
				LocalDateTime.now().minusMonths(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")), 2);
		Misura misura1 = new Misura(93.5,
				LocalDateTime.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")), 2);
		Misura misura2 = new Misura(73.5,
				LocalDateTime.now().minusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")), 2);
		Misura misura3 = new Misura(86.5,
				LocalDateTime.now().minusDays(6).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")), 2);
		Misura misura4 = new Misura(99.5,
				LocalDateTime.now().minusDays(18).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")), 2);
		Misura misura5 = new Misura(89.5,
				LocalDateTime.now().minusDays(20).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")), 2);
		Misura misura6 = new Misura(74.5,
				LocalDateTime.now().minusMonths(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")), 2);
		Misura misura7 = new Misura(72.5,
				LocalDateTime.now().minusHours(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")), 2);
		Misura misura8 = new Misura(78.5,
				LocalDateTime.now().minusMinutes(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")), 2);
		Misura misura9 = new Misura(72.5,
				LocalDateTime.now().minusMinutes(50).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")), 2);
		Misura misura10 = new Misura(96.5,
				LocalDateTime.now().minusMinutes(34).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")), 2);
		
		
		List<Misura> misure = new ArrayList<>();
		
		misure.add(misura);
		misure.add(misura1);
		misure.add(misura2);
		misure.add(misura3);
		misure.add(misura4);
		misure.add(misura5);
		misure.add(misura6);
		misure.add(misura7);
		misure.add(misura8);
		misure.add(misura9);
		misure.add(misura10);
		
		/*
		for (Misura mis : misure) {
			daoMisura.addMisura(mis);
		}
		
		 */
		
		System.out.println(data.getRetention("2Y"));
		
		
	}
	
	private String getRetention(String retentionS)
			throws Exception {
		String retention;
		
		
		if (retentionS.contains("Y") && retentionS.contains("M") || retentionS.contains("y") && retentionS.contains(
				"m")) {
			int years = Integer.parseInt(retentionS.split("[A-Za-z]")[0]);
			int months = Integer.parseInt(retentionS.split("[A-Za-z]")[1]);
			
			int totalMonths = years * 12 + months + LocalDateTime.now().getMonthValue() - 1;
			
			LocalDateTime result = LocalDateTime.now()
					.minusMonths(totalMonths)
					.with(TemporalAdjusters.firstDayOfMonth());
			
			System.out.println(result);
			
			retention = result.format(formatterData);
		} else if (retentionS.contains("Y") || retentionS.contains("y")) {
			if (retentionS.contains("Y")) {
				int years = Integer.parseInt(retentionS.replace("Y", ""));
				
				int totalMonths = years * 12 + LocalDateTime.now().getMonthValue() - 1;
				
				LocalDateTime result = LocalDateTime.now()
						.minusMonths(totalMonths)
						.with(TemporalAdjusters.firstDayOfMonth());
				
				System.out.println(result);
				
				retention = result.format(formatterData);
			} else {
				int years = Integer.parseInt(retentionS.replace("y", ""));
				
				int totalMonths = years * 12 + LocalDateTime.now().getMonthValue() - 1;
				
				LocalDateTime result = LocalDateTime.now()
						.minusMonths(totalMonths)
						.with(TemporalAdjusters.firstDayOfMonth());
				System.out.println(result);
				
				retention = result.format(formatterData);
			}
		} else if (retentionS.contains("M") || retentionS.contains("m")) {
			if (retentionS.contains("M")) {
				int months = Integer.parseInt(retentionS.replace("M", ""));
				
				LocalDateTime result = LocalDateTime.now()
						.minusMonths(months - 1)
						.with(TemporalAdjusters.firstDayOfMonth());
				
				System.out.println(result);
				
				retention = result.format(formatterData);
			} else {
				int months = Integer.parseInt(retentionS.replace("m", ""));
				
				LocalDateTime result = LocalDateTime.now()
						.minusMonths(months - 1)
						.with(TemporalAdjusters.firstDayOfMonth());
				
				System.out.println(result);
				
				retention = result.format(formatterData);
			}
		} else {
			throw new Exception();
		}
		
		
		return retention;
	}
	
}
