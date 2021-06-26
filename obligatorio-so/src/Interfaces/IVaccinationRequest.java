package Interfaces;

import Utils.Priority;
import Utils.Vaccine;

public interface IVaccinationRequest {
    String getKey();
    Vaccine getVaccine();
    Priority getPriority();
}
