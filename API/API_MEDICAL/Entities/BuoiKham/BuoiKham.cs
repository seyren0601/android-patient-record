using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace API_MEDICAL.Entities.BuoiKham
{
    [Table("BUOI_KHAM", Schema="patient_record")]
    public class BuoiKham
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int ID { get; set; }
        [ForeignKey("BenhNhan")]
        public int ID_BENH_NHAN { get; set; }
        public DateTime NGAY { get; set; }
        [ForeignKey("Benh")]
        public string? ICD { get; set; }
        public string? LY_DO_DEN_KHAM { get; set; }
        public int MACH { get; set; }
        public int NHIP_THO { get; set; }
        public int HUYET_AP { get; set; }
        public float CHIEU_CAO { get; set; }
        public int CAN_NANG { get; set; }
        public float BMI { get; set; }
        public float THAN_NHIET { get; set; }
        public string? TRIEU_CHUNG_LS { get; set; }
        public string? ICD_BENH_PHU { get; set; }
        public string? LOI_DAN_KHAM { get; set; }
        public string? KET_QUA_DIEU_TRI { get; set; }
        public DateTime NGAY_TAI_KHAM { get; set; }
        public int SO_NGAY_LAY_THUOC { get; set; }
    }
}
