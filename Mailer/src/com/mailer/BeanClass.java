package com.mailer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BeanClass {
	    @XmlElement String From;
	    @XmlElement String Password;
	    @XmlElement String To;
	    @XmlElement String Subject;
	    @XmlElement String Message;
}
