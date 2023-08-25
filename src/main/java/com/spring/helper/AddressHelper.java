package com.spring.helper;

import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.spring.constant.Constant;
import com.spring.dao.AddressDetailsDao;
import com.spring.entities.AddressDetails;
import com.spring.entities.UserDetails;
import com.spring.exceptions.BizException;
import com.spring.object.request.AddressRequestObject;
import com.spring.object.request.UserRequestObject;

@Component
public class AddressHelper {

	@Autowired
	private AddressDetailsDao addressDetailsDao;

	public void validateAddressRequest(AddressRequestObject addressRequestObject) throws BizException {
		if (addressRequestObject == null) {
			throw new BizException(Constant.BAD_REQUEST_CODE, "Bad Request Object Null");
		}
	}

	public static String generateRandomChars(String candidateChars, int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
		}
		return sb.toString();
	}

	@Transactional
	public AddressDetails getAddressDetailsByReqObj(AddressRequestObject addressRequest, Long id, String superadminId) {

		AddressDetails addressDetails = new AddressDetails();

		addressDetails.setUserId(id);
		addressDetails.setUserType(addressRequest.getUserType());
		addressDetails.setAddressType(addressRequest.getAddressType());
		addressDetails.setAddressLine(addressRequest.getAddressLine());
		addressDetails.setLandmark(addressRequest.getLandmark());
		addressDetails.setDistrict(addressRequest.getDistrict());
		addressDetails.setCity(addressRequest.getCity());
		addressDetails.setState(addressRequest.getState());
		addressDetails.setCountry("INDIA");
		addressDetails.setPincode(addressRequest.getPincode());

		addressDetails.setCreatedAt(new Date());
		addressDetails.setSuperadminId(superadminId);

		return addressDetails;
	}

	@Transactional
	public AddressDetails saveAddressDetails(AddressDetails addressDetails) {
		addressDetailsDao.persist(addressDetails);
		return addressDetails;
	}

	@SuppressWarnings("unchecked")
	public List<AddressDetails> getAddressDetails(UserRequestObject userRequest) {

		String hqlQuery = "";
		if (userRequest.getRequestedFor().equals("ALL")) {
			hqlQuery = "SELECT UD FROM AddressDetails UD";
		} else if (userRequest.getRequestedFor().equals("STUDENT")) {

		}
		List<AddressDetails> results = addressDetailsDao.getEntityManager().createQuery(hqlQuery).getResultList();
		return results;
	}

}
