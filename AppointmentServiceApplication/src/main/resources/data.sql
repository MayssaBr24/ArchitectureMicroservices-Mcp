-- Initial appointments
INSERT INTO appointments (id, patient_id, patient_name, date_time, type, doctor_name, department, status, notes, created_at)
VALUES
    (UUID(), 'P001', 'Jean Dupont', DATE_ADD(NOW(), INTERVAL 1 DAY), 'Consultation', 'Dr. Martin', 'Cardiologie', 'SCHEDULED', 'Contrôle annuel', NOW()),
    (UUID(), 'P002', 'Sophie Martin', DATE_ADD(NOW(), INTERVAL 2 DAY), 'Urgence', 'Dr. Bernard', 'Pédiatrie', 'CONFIRMED', 'Fièvre persistante', NOW()),
    (UUID(), 'P003', 'Pierre Bernard', DATE_ADD(NOW(), INTERVAL 3 DAY), 'Suivi', 'Dr. Dubois', 'Orthopédie', 'SCHEDULED', 'Suivi fracture', NOW()),
    (UUID(), 'P004', 'Marie Dubois', DATE_ADD(NOW(), INTERVAL 4 DAY), 'Consultation', 'Dr. Dupont', 'Gynécologie', 'COMPLETED', 'Examen de routine', NOW());