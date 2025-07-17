import React, { useEffect, useState } from "react";
import {
  saveFormDataToSession,
  getFormDataFromSession,
} from "../../utils/dataUtils";

const LeaveDetailsForm = ({ formData, handleInputChange }) => {
  const [localFormData, setLocalFormData] = useState(
    getFormDataFromSession() || formData
  );

  // Load previous form data on mount
  useEffect(() => {
    setLocalFormData(getFormDataFromSession() || formData);
  }, []);

  // Save data on change
  const handleChange = (e) => {
    const { name, value } = e.target;
    const updatedData = { ...localFormData, [name]: value };
    setLocalFormData(updatedData);
    handleInputChange(e); // Update parent state
    saveFormDataToSession(updatedData); // Persist data
  };

  return (
    <div className="container mt-4 p-2">
      {/* Institute Heading */}
      <div className="text-center mb-4">
        <h5 className="fw-bold">PUNE INSTITUTE OF COMPUTER TECHNOLOGY</h5>
        <p className="mb-0">DHANKAWADI, PUNE - 411 043</p>
        <h6 className="fw-bold mt-2">LEAVE APPLICATION</h6>
        <hr />
      </div>
      <div className="row">
        {/* Name & Post */}
        <div className="col-md-6 mb-3">
          <label className="form-label">1) Name of the Applicant:</label>
          <input
            type="text"
            name="applicantName"
            className="form-control"
            value={localFormData.applicantName || ""}
            onChange={handleChange}
          />
        </div>
        <div className="col-md-6 mb-3">
          <label className="form-label">2) Post Held:</label>
          <input
            type="text"
            name="postHeld"
            className="form-control"
            value={localFormData.postHeld || ""}
            onChange={handleChange}
          />
        </div>

        {/* Leave Days & Nature */}
        <div className="col-md-6 mb-3">
          <label className="form-label">3) No of Days of Leave Required:</label>
          <input
            type="number"
            name="numberOfDays"
            className="form-control"
            value={localFormData.numberOfDays || ""}
            onChange={handleChange}
          />
        </div>
        <div className="col-md-6 mb-3">
          <label className="form-label">4) Nature of Leave:</label>
          <select
            name="natureOfLeave"
            className="form-select"
            value={localFormData.natureOfLeave || ""}
            onChange={handleChange}
          >
            <option value="">Select Leave Type</option>
            <option>Casual Leave</option>
            <option>Medical Leave</option>
            <option>Earned Leave</option>
            <option>"C" Off</option>
            <option>LWP</option>
          </select>
        </div>

        {/* Document Upload for Medical Leave */}
        {localFormData.natureOfLeave === "Medical Leave" && (
          <div className="col-md-6 mb-3 offset-md-6">
            <label className="form-label">Upload Medical Document:</label>
            <input
              type="file"
              name="medicalDocument"
              className="form-control"
              onChange={handleChange}
            />
          </div>
        )}

        {/* Leave Period */}
        <div className="col-md-6 mb-3">
          <label className="form-label">5) Period of Leave (From):</label>
          <input
            type="date"
            name="startDate"
            className="form-control"
            value={localFormData.startDate || ""}
            onChange={handleChange}
          />
        </div>
        <div className="col-md-6 mb-3">
          <label className="form-label">To:</label>
          <input
            type="date"
            name="endDate"
            className="form-control"
            value={localFormData.endDate || ""}
            onChange={handleChange}
          />
        </div>

        {/* Address & Reason */}
        <div className="col-md-6 mb-3">
          <label className="form-label">6) Leave Address:</label>
          <input
            type="text"
            name="leaveAddress"
            className="form-control"
            value={localFormData.leaveAddress || ""}
            onChange={handleChange}
          />
        </div>
        <div className="col-md-6 mb-3">
          <label className="form-label">7) Phone Number:</label>
          <input
            type="text"
            name="phoneNumber"
            className="form-control"
            value={localFormData.phoneNumber || ""}
            onChange={handleChange}
          />
        </div>
        <div className="col-md-12 mb-3">
          <label className="form-label">8) Reason:</label>
          <textarea
            name="reason"
            className="form-control"
            value={localFormData.reason || ""}
            onChange={handleChange}
          ></textarea>
        </div>
      </div>
    </div>
  );
};

export default LeaveDetailsForm;
