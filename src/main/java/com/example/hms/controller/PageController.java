package com.example.hms.controller;

import com.example.hms.model.*;
import com.example.hms.repository.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Controller
public class PageController {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final BillingRepository billingRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final LabTestRepository labTestRepository;
    private final MedicineRepository medicineRepository;
    private final StaffRepository staffRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public PageController(UserRepository userRepository, PatientRepository patientRepository,
                           DoctorRepository doctorRepository, AppointmentRepository appointmentRepository,
                           BillingRepository billingRepository, MedicalRecordRepository medicalRecordRepository,
                           LabTestRepository labTestRepository, MedicineRepository medicineRepository,
                           StaffRepository staffRepository) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.billingRepository = billingRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.labTestRepository = labTestRepository;
        this.medicineRepository = medicineRepository;
        this.staffRepository = staffRepository;
    }

    // ---- Auth ----
    @GetMapping("/")
    public String rootPage() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, Model model) {
        Optional<User> userOpt = userRepository.findAll().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();

        if (userOpt.isEmpty() || !passwordEncoder.matches(password, userOpt.get().getPassword())) {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }

        model.addAttribute("user", userOpt.get());
        return "dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard";
    }

    // ---- Patients ----
    @GetMapping("/patients-page")
    public String patientsPage(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        return "patients-page";
    }

    @PostMapping("/patients-page/add")
    public String addPatient(Patient patient) {
        patientRepository.save(patient);
        return "redirect:/patients-page";
    }

    // ---- Doctors ----
    @GetMapping("/doctors-page")
    public String doctorsPage(Model model) {
        model.addAttribute("doctors", doctorRepository.findAll());
        return "doctors-page";
    }

    @PostMapping("/doctors-page/add")
    public String addDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
        return "redirect:/doctors-page";
    }

    // ---- Appointments ----
    @GetMapping("/appointments-page")
    public String appointmentsPage(Model model) {
        model.addAttribute("appointments", appointmentRepository.findAll());
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("doctors", doctorRepository.findAll());
        return "appointments-page";
    }

    @PostMapping("/appointments-page/add")
    public String addAppointment(@RequestParam Long patientId, @RequestParam Long doctorId,
                                  @RequestParam String appointmentDate, @RequestParam String appointmentTime) {
        Appointment appointment = new Appointment();
        appointment.setPatient(patientRepository.findById(patientId).orElse(null));
        appointment.setDoctor(doctorRepository.findById(doctorId).orElse(null));
        appointment.setAppointmentDate(LocalDate.parse(appointmentDate));
        appointment.setAppointmentTime(LocalTime.parse(appointmentTime));
        appointmentRepository.save(appointment);
        return "redirect:/appointments-page";
    }

    // ---- Billing ----
    @GetMapping("/billing-page")
    public String billingPage(Model model) {
        model.addAttribute("bills", billingRepository.findAll());
        model.addAttribute("appointments", appointmentRepository.findAll());
        return "billing-page";
    }

    @PostMapping("/billing-page/add")
    public String addBilling(@RequestParam Long appointmentId, @RequestParam Double consultationFee,
                              @RequestParam Double labCharges, @RequestParam Double pharmacyCharges) {
        Billing billing = new Billing();
        billing.setAppointment(appointmentRepository.findById(appointmentId).orElse(null));
        billing.setConsultationFee(consultationFee);
        billing.setLabCharges(labCharges);
        billing.setPharmacyCharges(pharmacyCharges);
        billing.setTotalAmount(consultationFee + labCharges + pharmacyCharges);
        billingRepository.save(billing);
        return "redirect:/billing-page";
    }

    // ---- Medical Records ----
    @GetMapping("/medical-records-page")
    public String medicalRecordsPage(Model model) {
        model.addAttribute("records", medicalRecordRepository.findAll());
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("doctors", doctorRepository.findAll());
        return "medical-records-page";
    }

    @PostMapping("/medical-records-page/add")
    public String addMedicalRecord(@RequestParam Long patientId, @RequestParam Long doctorId,
                                    @RequestParam String diagnosis, @RequestParam String prescription,
                                    @RequestParam String notes) {
        MedicalRecord record = new MedicalRecord();
        record.setPatient(patientRepository.findById(patientId).orElse(null));
        record.setDoctor(doctorRepository.findById(doctorId).orElse(null));
        record.setDiagnosis(diagnosis);
        record.setPrescription(prescription);
        record.setNotes(notes);
        medicalRecordRepository.save(record);
        return "redirect:/medical-records-page";
    }

    // ---- Lab Tests ----
    @GetMapping("/lab-tests-page")
    public String labTestsPage(Model model) {
        model.addAttribute("tests", labTestRepository.findAll());
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("doctors", doctorRepository.findAll());
        return "lab-tests-page";
    }

    @PostMapping("/lab-tests-page/add")
    public String addLabTest(@RequestParam Long patientId, @RequestParam Long doctorId,
                              @RequestParam String testName, @RequestParam(required = false) String result) {
        LabTest test = new LabTest();
        test.setPatient(patientRepository.findById(patientId).orElse(null));
        test.setDoctor(doctorRepository.findById(doctorId).orElse(null));
        test.setTestName(testName);
        test.setResult(result);
        if (result != null && !result.isEmpty()) {
            test.setStatus("COMPLETED");
        }
        labTestRepository.save(test);
        return "redirect:/lab-tests-page";
    }

    // ---- Medicines ----
    @GetMapping("/medicines-page")
    public String medicinesPage(Model model) {
        model.addAttribute("medicines", medicineRepository.findAll());
        return "medicines-page";
    }

    @PostMapping("/medicines-page/add")
    public String addMedicine(Medicine medicine) {
        medicineRepository.save(medicine);
        return "redirect:/medicines-page";
    }

    // ---- Staff ----
    @GetMapping("/staff-page")
    public String staffPage(Model model) {
        model.addAttribute("staffList", staffRepository.findAll());
        return "staff-page";
    }

    @PostMapping("/staff-page/add")
    public String addStaff(Staff staff) {
        staffRepository.save(staff);
        return "redirect:/staff-page";
    }
}