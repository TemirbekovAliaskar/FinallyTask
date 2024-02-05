package java12;

import java12.config.PostgresConnection;
import java12.dao.HouseDao;
import java12.entities.*;
import java12.entities.enums.FamilyStatus;
import java12.entities.enums.Gender;
import java12.entities.enums.HouseType;
import java12.service.*;
import java12.service.impl.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
//        PostgresConnection.entityManagerFactory();
//        System.out.println( "Hello World!" );

        AgencyService agencyService = new AgencyImpl();
        AddressService addressService = new AddressImpl();
        CustumerService custumerService = new CustumerImpl();
        OwnerService ownerService = new OwnerImpl();
        HouseService houseService = new HouseImpl();
        RentInfoService rentInfoService = new RentInfoImpl();
        Scanner scannerNum = new Scanner(System.in);
        Scanner scannerWor = new Scanner(System.in);

        List<House> houses = new ArrayList<>(Arrays.asList(
                new House(HouseType.APARTMENT, new BigDecimal(60000), 8.6, "Very well", 9, true,new Address("Parig","Terr","POPO")),
                new House(HouseType.HOUSE, new BigDecimal(78900), 9.4, "Beatiful ", 7, false,new Address("WERT","RTY","TYU"))
        ));

        while (true) {
            System.out.println("""

                         Agency
                    1.Save
                    2.FindByID
                    3.FindAll
                    4.Update
                    5.Delete

                        Address
                    6.FindByID
                    7.FindAll
                    8.Update
                    9.GetAddressToAgency
                    10.getAgencyByAddressName
                    11.GroupByRegion

                        Customer
                    12.Save
                    13.saveCustomerWithRent
                    14.FindByID
                    15.FindAll
                    16.Update
                    17.Delete

                        Owner
                    18.Save
                    19.SaveToHouse
                    20.FindByID
                    21.FindAll
                    22.Update
                    23.DeleteOwner
                    24.Assign
                    25.GetAllOwnerToAgencyID

                         __House__
                    26.Save
                    27.SaveToHouse
                    28.FindByID
                    29.FindAll
                    30.Update
                    31.Delete               
                    32.getRegion
                    33.getAgencyHouses
                    34.getOwnerHouses
                    35.getCheck
                    
                    36.GtInfoCheckouts
                    37.CountHousesNo


                    """);
            switch (scannerNum.nextInt()) {
                case 1 -> {
                    System.out.println("Save Agency");
                    Agency agency = new Agency();
                    Address address = new Address();
                    System.out.print("Name : ");
                    agency.setName(scannerWor.nextLine());
                    System.out.print("Number :");
                    agency.setPhoneNumber(scannerWor.nextLine());
                    System.out.print("Country :");
                    address.setCity(scannerWor.nextLine());
                    System.out.print("Region :");
                    address.setRegion(scannerWor.nextLine());
                    System.out.print("Street :");
                    address.setStreet(scannerWor.nextLine());
                    System.out.println(agencyService.saveAgency(agency, address));
                }
                case 2 -> System.out.println(agencyService.getAgencyById(1L));
                case 3 -> System.out.println(agencyService.getAllAgency());
                case 4 -> agencyService.update(1L, new Agency("Mirla", "3435325235", new Address("sdfgh", "hgfdsa", "ytrew")));
                case 5 -> agencyService.deleteAgency(1L);


                case 6 ->  System.out.println(addressService.getAddressById(1L));
                case 7 ->  System.out.println(addressService.getAllAddress());
                case 8 ->  addressService.update(1L, new Address("KAra", "TUU", "Saqq"));
                case 9 ->  System.out.println(addressService.getAddressToAgency());
                case 10 -> System.out.println(addressService.getAgencyByAddressName("KAra"));
                case 11 -> System.out.println(addressService.groupByRegion());


                case 12 -> custumerService.saveCustumer(new Custumer("Argen", "Urumov", "a@mail.com", LocalDate.of(2000, 1, 3), Gender.MALE, "Kyrgyz", FamilyStatus.SINGLE));
                case 13 -> custumerService.saveCustomerWithRent(new Custumer("Adis", "Temirbekov", "adi@mail.com", LocalDate.of(2002, 11, 12), Gender.MALE, "Kyrgyz", FamilyStatus.SINGLE), 1L, 1L, LocalDate.of(2023, 4, 5), LocalDate.of(2023, 4, 9));
                case 14 -> System.out.println(custumerService.getCustumerById(1L));
                case 15 -> System.out.println(custumerService.getAllCustumer());
                case 16 -> custumerService.update(1L, new Custumer("Ali", "Tumov", "ali@mail.com", LocalDate.of(2004, 1, 3), Gender.MALE, "Kyrgyz", FamilyStatus.MARRIED));
                case 17 -> custumerService.deleteCustumer(1L);

                case 18 -> ownerService.saveOwner(new Owner("Askar", "Erlanov", "aqq@gmail.com", LocalDate.of(2000, 3, 5), Gender.MALE));
                case 19 -> ownerService.saveOwner(new Owner("Asyl", "Kadenova", "as@gmail.com", LocalDate.of(2002, 8, 15), Gender.FEMALE), houses);
                case 20 -> System.out.println(ownerService.getOwnerById(1L));
                case 21 -> System.out.println(ownerService.getAllOwner());
                case 22 -> ownerService.update(1L,new Owner("Askar","ASAS","qq@mail.com",LocalDate.of(2003,1,4),Gender.MALE));
                case 23 -> ownerService.deleteOwner(1L);
                case 24 -> ownerService.asSignOwnerToAgency(1L,1L);
                case 25 -> ownerService.getAllOwnerToAgencyID(1L);

                case 26 -> houseService.saveHouse(new House(HouseType.APARTMENT,new BigDecimal(20000),6.7,"Big Home",10,true,new Address("Uels","ASSA","Tour56")));
                case 27 -> houseService.saveHouse(new Owner("Myrzaiym","Keldibekova","m@mail.com",LocalDate.of(2001,12,4),Gender.FEMALE),houses);
                case 28 -> System.out.println(houseService.getHouseById(1L));
                case 29 -> System.out.println(houseService.getAllHouse());
                case 30 -> houseService.update(1L,new House(HouseType.APARTMENT,new BigDecimal(20000),6.7,"Big Home",10,true,new Address("Uels","ASSA","Tour56")));
                case 31 -> houseService.deleteHouse(1L);
                case 32 -> System.out.println(houseService.getRegion("ASSA"));
                case 33 -> System.out.println(houseService.getAgencyHouses(1L));
                case 34 -> System.out.println(houseService.getOwnerHouses(1L));
                case 35 -> System.out.println(houseService.getCheck(LocalDate.of(2023, 4, 5), LocalDate.of(2024, 5, 2)));

                case 36 -> System.out.println(rentInfoService.getInfoCheckouts(LocalDate.of(2023, 4, 5), LocalDate.of(2023, 4, 10)));
                case 37 -> rentInfoService.countHousesNow(1L);

            }


        }
    }
    }

