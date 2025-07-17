import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
  headers: { "Content-Type": "application/json" },
});

// Function to Set Authorization Header Dynamically
export const setAuthToken = (token) => {
  if (token) {
    api.defaults.headers.Authorization = `Bearer ${token}`;
    console.log("Authorization token set:", token);
  } else {
    delete api.defaults.headers.Authorization;
    console.log("Authorization token removed");
  }
};

//get all pending requests
export const fetchPendingRequests = async () => {
  try {
    const response = await api.get(`/inbox/getAllPendingMessages`);
    return response.data;
  } catch (error) {
    console.error("Error fetching pending requests:", error);
    return { data: [] };
  }
};

//get request by id
export const fetchRequestById = async (id) => {
  try {
    const response = await api.get(`/inbox/getAllPendingMessages/${id}`);
    return response.data;
  } catch (error) {
    console.error("Error fetching request by id:", error);
    return { data: {} };
  }
};

//get approved requests
export const fetchApprovedRequests = async () => {
  try {
    const response = await api.get(`/inbox/getAllApprovedMessages`);
    return response.data;
  } catch (error) {
    console.error("Error fetching approved requests:", error);
    return { data: [] };
  }
};

//accept application
export const acceptApplication = async (id) => {
  try {
    const response = await api.post(`/HOD/acceptLeaveApplication/${id}`);
    return response.data;
  } catch (error) {
    console.error("Error accepting application:", error);
    return { status: "Failed" };
  }
};

//reject application
export const rejectApplication = async (id) => {
  try {
    const response = await api.post(`/HOD/rejectLeaveApplication/${id}`);
    return response.data;
  } catch (error) {
    console.error("Error rejecting application:", error);
    return { status: "Failed" };
  }
};
