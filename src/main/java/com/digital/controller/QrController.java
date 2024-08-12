package com.digital.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.digital.model.User;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;
//import com.google.zxing.user.QRCodeWriter;

//import com.pogo.user.User;

@Controller
@RequestMapping("/qr")
public class QrController {
	
	@ModelAttribute("qr")
	public User user() {
		
		return new User();
	}
	
	@GetMapping
	public String homePage() {
		
		return "index";
	}
//}
	
	@PostMapping
	public String genrateQRCode(@ModelAttribute("qr") User user, Model model) {
		
		try {
			BufferedImage bufferedImage = genrateQRCodeImage(user);
			
			File output = new File("C:\\Users\\Avishkar\\Documents\\Lumion 11.0\\Projects"+user.getFirstName()+".jpg");
			
			ImageIO.write(bufferedImage, "jpg", output);
			
			model.addAttribute("qr",user);
			
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/qr?success";
	}

	public static BufferedImage genrateQRCodeImage(User user) throws WriterException {
		
		StringBuilder str = new StringBuilder();
		str = str.append("First Name:").append(user.getFirstName()).append("| |").append("Last Name:").append(user.getLastName())
				.append("| |").append("City:").append(user.getCity()).append("| |").append("State:").append(user.getState())
				.append("| |").append("Zip Code:").append(user.getZipCode());
		
		QRCodeWriter codeWriter = new QRCodeWriter();
		
		
		BitMatrix bitMatrix = codeWriter.encode(str.toString(), BarcodeFormat.QR_CODE, 200, 200);
		
		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}

}

	

