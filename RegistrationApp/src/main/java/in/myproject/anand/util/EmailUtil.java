package in.myproject.anand.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import in.myproject.anand.Exception.RegAppException;

@Component
public class EmailUtil {


	@Autowired
	private JavaMailSender mailSender;


	public boolean sendEmail(String subject,String to,String body)
	{

		boolean isEmailSent=false;
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setText(body,true);

			mailSender.send(mimeMessageHelper.getMimeMessage());
			isEmailSent=true;
		} catch (Exception e) {
				throw new RegAppException(e.getMessage());
		} 

		return isEmailSent;
	}

}
