package com.ngo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.common.constant.Constant;
import com.common.exceptions.BizException;
import com.ngo.entities.DonationDetails;
import com.ngo.object.request.DonationRequestObject;
import com.ngo.pdf.DonationReceiptPdf;
import com.ngo.services.DonationService;
import com.common.object.request.Request;
import com.common.object.response.GenricResponse;
import com.common.object.response.Response;


@CrossOrigin(origins = "*")
@RestController
public class DonationController {
	
	@Autowired
	private DonationService donationService;
	
	@Autowired
	private DonationReceiptPdf donationReceiptPdf;
	
	@GetMapping("/download-donation-receipt") 
	public ResponseEntity<byte[]> downloadReceipt() throws Exception {


	    byte[] data = donationReceiptPdf.generateInvoice();

	    return ResponseEntity.ok()
	            .header("Content-Disposition", "attachment; filename=donation-receipt.pdf")
	            .contentType(MediaType.APPLICATION_PDF)
	            .body(data);
	}

	
	@RequestMapping(path = "updateDonationCurrency", method = RequestMethod.POST)
	public Response<DonationRequestObject>updateDonationCurrency(@RequestBody Request<DonationRequestObject> donationRequestObject, HttpServletRequest request)
	{
		GenricResponse<DonationRequestObject> responseObj = new GenricResponse<DonationRequestObject>();
		try {
			DonationRequestObject responce =  donationService.updateDonationCurrency(donationRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	
	@RequestMapping(path = "addDonation", method = RequestMethod.POST)
	public Response<DonationRequestObject>addDonation(@RequestBody Request<DonationRequestObject> donationRequestObject, HttpServletRequest request)
	{
		GenricResponse<DonationRequestObject> responseObj = new GenricResponse<DonationRequestObject>();
		try {
			DonationRequestObject responce =  donationService.addDonation(donationRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "updateDonation", method = RequestMethod.POST)
	public Response<DonationRequestObject>updateDonation(@RequestBody Request<DonationRequestObject> donationRequestObject, HttpServletRequest request)
	{
		GenricResponse<DonationRequestObject> responseObj = new GenricResponse<DonationRequestObject>();
		try {
			DonationRequestObject responce =  donationService.updateDonation(donationRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "updateDonationStatus", method = RequestMethod.POST)
	public Response<DonationRequestObject>updateDonationStatus(@RequestBody Request<DonationRequestObject> donationRequestObject, HttpServletRequest request)
	{
		GenricResponse<DonationRequestObject> responseObj = new GenricResponse<DonationRequestObject>();
		try {
			DonationRequestObject responce =  donationService.updateDonationStatus(donationRequestObject);
			return responseObj.createSuccessResponse(responce, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getDonationList", method = RequestMethod.POST)
	public Response<DonationDetails> getDonationList(@RequestBody Request<DonationRequestObject> donationRequestObject) {
		GenricResponse<DonationDetails> response = new GenricResponse<DonationDetails>();
		try {
			List<DonationDetails> donationList = donationService.getDonationList(donationRequestObject);
//			return response.createListResponse(donationList, Constant.SUCCESS_CODE);
			return response.createListResponse(donationList, Constant.SUCCESS_CODE, String.valueOf(donationList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getDonationListBySearchKey", method = RequestMethod.POST)
	public Response<DonationDetails> getDonationListBySearchKey(@RequestBody Request<DonationRequestObject> donationRequestObject) {
		GenricResponse<DonationDetails> response = new GenricResponse<DonationDetails>();
		try {
			List<DonationDetails> donationList = donationService.getDonationListBySearchKey(donationRequestObject);
			return response.createListResponse(donationList, Constant.SUCCESS_CODE, String.valueOf(donationList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getDonationListByReceiptNumber", method = RequestMethod.POST)
	public Response<DonationDetails> getDonationListByReceiptNumber(@RequestBody Request<DonationRequestObject> donationRequestObject) {
		GenricResponse<DonationDetails> response = new GenricResponse<DonationDetails>();
		try {
			List<DonationDetails> donationList = donationService.getDonationListByReceiptNumber(donationRequestObject);
			return response.createListResponse(donationList, Constant.SUCCESS_CODE, String.valueOf(donationList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getCountAndSum", method = RequestMethod.POST)
	public Response<DonationRequestObject>getCountAndSum(@RequestBody Request<DonationRequestObject> donationRequestObject,  HttpServletRequest request)
	{
		GenricResponse<DonationRequestObject> responseObj = new GenricResponse<DonationRequestObject>();
		try {
			DonationRequestObject response =  donationService.getCountAndSum(donationRequestObject);
			return responseObj.createSuccessResponse(response, Constant.SUCCESS_CODE);
		}catch (BizException e) {
			return responseObj.createErrorResponse(Constant.BAD_REQUEST_CODE,e.getMessage());
		} 
 		catch (Exception e) {
 			e.printStackTrace();
			return responseObj.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getStartPerformer", method = RequestMethod.POST)
	public Response<DonationDetails> getStartPerformer(@RequestBody Request<DonationRequestObject> donationRequestObject) {
		GenricResponse<DonationDetails> response = new GenricResponse<DonationDetails>();
		try {
			List<DonationDetails> startPerformerList = donationService.getStartPerformer(donationRequestObject);
			return response.createListResponse(startPerformerList, Constant.SUCCESS_CODE, String.valueOf(startPerformerList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getStartTeam", method = RequestMethod.POST)
	public Response<DonationDetails> getStartTeam(@RequestBody Request<DonationRequestObject> donationRequestObject) {
		GenricResponse<DonationDetails> response = new GenricResponse<DonationDetails>();
		try {
			List<DonationDetails> startPerformerList = donationService.getStartTeam(donationRequestObject);
			return response.createListResponse(startPerformerList, Constant.SUCCESS_CODE, String.valueOf(startPerformerList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getDonationCountAndAmountGroupByName", method = RequestMethod.POST)
	public Response<DonationDetails> getDonationCountAndAmountGroupByName(@RequestBody Request<DonationRequestObject> donationRequestObject) {
		GenricResponse<DonationDetails> response = new GenricResponse<DonationDetails>();
		try {
			List<DonationDetails> donationList = donationService.getDonationCountAndAmountGroupByName(donationRequestObject);
			return response.createListResponse(donationList, Constant.SUCCESS_CODE, String.valueOf(donationList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getDonationCountAndAmountGroupByCurrency", method = RequestMethod.POST)
	public Response<DonationDetails> getDonationCountAndAmountGroupByCurrency(@RequestBody Request<DonationRequestObject> donationRequestObject) {
		GenricResponse<DonationDetails> response = new GenricResponse<DonationDetails>();
		try {
			List<DonationDetails> donationList = donationService.getDonationCountAndAmountGroupByCurrency(donationRequestObject);
			return response.createListResponse(donationList, Constant.SUCCESS_CODE, String.valueOf(donationList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getDonationPaymentModeCountAndAmountGroupByName", method = RequestMethod.POST)
	public Response<DonationDetails> getDonationPaymentModeCountAndAmountGroupByName(@RequestBody Request<DonationRequestObject> donationRequestObject) {
		GenricResponse<DonationDetails> response = new GenricResponse<DonationDetails>();
		try {
			List<DonationDetails> donationList = donationService.getDonationPaymentModeCountAndAmountGroupByName(donationRequestObject);
			return response.createListResponse(donationList, Constant.SUCCESS_CODE, String.valueOf(donationList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getDonationPaymentModeCountAndAmountGroupByPaymentMode", method = RequestMethod.POST)
	public Response<DonationDetails> getDonationPaymentModeCountAndAmountGroupByPaymentMode(@RequestBody Request<DonationRequestObject> donationRequestObject) {
		GenricResponse<DonationDetails> response = new GenricResponse<DonationDetails>();
		try {
			List<DonationDetails> donationList = donationService.getDonationPaymentModeCountAndAmountGroupByPaymentMode(donationRequestObject);
			return response.createListResponse(donationList, Constant.SUCCESS_CODE, String.valueOf(donationList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getDonationProgramNameCountAndAmountGroupByName", method = RequestMethod.POST)
	public Response<DonationDetails> getDonationProgramNameCountAndAmountGroupByName(@RequestBody Request<DonationRequestObject> donationRequestObject) {
		GenricResponse<DonationDetails> response = new GenricResponse<DonationDetails>();
		try {
			List<DonationDetails> donationList = donationService.getDonationProgramNameCountAndAmountGroupByName(donationRequestObject);
			return response.createListResponse(donationList, Constant.SUCCESS_CODE, String.valueOf(donationList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getDonationListForLead", method = RequestMethod.POST)
	public Response<DonationDetails> getDonationListForLead(
			@RequestBody Request<DonationRequestObject> donationRequestObject) {
		GenricResponse<DonationDetails> response = new GenricResponse<DonationDetails>();
		try {
			List<DonationDetails> donationList = donationService.getDonationListForLead(donationRequestObject);
			return response.createListResponse(donationList, Constant.SUCCESS_CODE,
					String.valueOf(donationList.size()));
		} catch (Exception e) {
			e.printStackTrace();
			return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
		}
	}
	
	@RequestMapping(path = "getDonationCountAndAmountGroupByNameNew", method = RequestMethod.POST)
	public Response<Object[]> getDonationCountAndAmountGroupByNameNew(
	        @RequestBody Request<DonationRequestObject> donationRequestObject) {
	    GenricResponse<Object[]> response = new GenricResponse<>();
	    try {
	        // Call the service method to fetch donation data
	        List<Object[]> donationList = donationService.getDonationCountAndAmountGroupByNameNew(donationRequestObject);

	        // Return the response with success code
	        return response.createListResponse(donationList, Constant.SUCCESS_CODE, String.valueOf(donationList.size()));

	    } catch (Exception e) {
	        e.printStackTrace();

	        // Return the error response in case of exception
	        return response.createErrorResponse(Constant.INTERNAL_SERVER_ERR, e.getMessage());
	    }
	}
	
}
