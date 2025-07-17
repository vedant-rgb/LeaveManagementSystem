import React, { useState, useEffect } from "react";
import LeaveDetailsForm from "./LeaveDetailsForm";
import AlternateArrangementsTable from "./AlternateArrangementsTable";
import submitLeaveApplication from '../../api/api'
import { saveFormDataToSession, getFormDataFromSession, saveLecturesToLocal, getLecturesFromLocal, clearAllStoredData } from "../../utils/dataUtils";

const ApplyLeave2 = () => {
  // Load form data from sessionStorage (resets on browser close)
  const [formData, setFormData] = useState(getFormDataFromSession() || {
    applicantName: "",
    postHeld: "",
    numberOfDays: "",
    natureOfLeave: "",
    reason: "",
    startDate: "",
    endDate: "",
    leaveAddress: "",
    phoneNumber: "",
  });

  // Load lectures from localStorage (persists across browser sessions)
  const [lectures, setLectures] = useState(getLecturesFromLocal() || []);

  // Save form data to sessionStorage on change
  useEffect(() => {
    saveFormDataToSession(formData);
  }, [formData]);

  // Save lectures to localStorage on change
  useEffect(() => {
    saveLecturesToLocal(lectures);
  }, [lectures]);

  // Handle input change in the leave form
  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setFormData({ ...formData, [name]: value });
  };

  // Handle input change in the alternate arrangements table
  const handleLectureChange = (index, field, value) => {
    const updatedLectures = [...lectures];
    updatedLectures[index][field] = value;
    setLectures(updatedLectures);
  };

  // Add a new empty row for alternate arrangements
  const addLectureRow = () => {
    setLectures([
      ...lectures,
      { date: "", startTime: "", endTime: "", division: "", subject: "", facultyId: "", requested: false }
    ]);
  };

  // Remove a row from the alternate arrangements table
  // const removeLectureRow = (index) => {
  //   const updatedLectures = storedLectures.filter((_, i) => i !== index);
  //   setStoredLectures(updatedLectures);
  //   saveLecturesToLocal(updatedLectures); // Update local storage
  // };
  

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();

    // 🔹 **Format the request payload as per required JSON structure**
    const requestData = {
      teacherRegistrationId: formData.teacherRegistrationId,
      applicantName: formData.applicantName,
      post: formData.postHeld,
      numberOfDays: formData.numberOfDays,
      startDate: formData.startDate,
      endDate: formData.endDate,
      natureOfLeave: formData.natureOfLeave,
      reason: formData.reason,
      leaveAddress: formData.leaveAddress,
      phoneNumber: formData.phoneNumber,
      alternateArrangements: lectures.map((lecture) => ({
        date: lecture.date,
        startTime: lecture.startTime,
        endTime: lecture.endTime,
        division: lecture.division,
        subject: lecture.subject,
        originalTeacherId: formData.teacherRegistrationId,
        substituteTeacherId: lecture.substituteTeacherId,
      })),
    };

    console.log("Submitting Data:", requestData);

    try {
      const response = await submitLeaveApplication(requestData);
      console.log("Server Response:", response);
      
      // Clear sessionStorage & localStorage on successful submission
      clearAllStoredData();
      
      // Reset form fields
      setFormData({
        teacherRegistrationId: "",
        applicantName: "",
        postHeld: "",
        numberOfDays: "",
        natureOfLeave: "",
        reason: "",
        startDate: "",
        endDate: "",
        leaveAddress: "",
        phoneNumber: "",
      });

      // Reset lecture arrangements
      setLectures([]);
    } catch (error) {
      console.error("Error submitting leave application:", error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <LeaveDetailsForm formData={formData} handleInputChange={handleInputChange} />
      <AlternateArrangementsTable
        lectures={lectures}
        handleLectureChange={handleLectureChange}
        addLectureRow={addLectureRow}
      />
      <div className="text-center mt-2">
        <button type="submit" className="btn btn-primary">
          Submit
        </button>
      </div>
    </form>
  );
};

export default ApplyLeave2;
