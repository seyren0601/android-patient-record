using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace API_MEDICAL.Entities
{
    [Table("BENH_NHAN", Schema="PATIENT_RECORD")]
    public class BenhNhan
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int ID { get; set; }
        [StringLength(15)]
        public string? BHYT { get; set; }
        [Required]
        public string TEN { get; set; }
        [Required]
        public DateTime NGAY_SINH { get; set; }
        public int? TUOI { get; set; }
        [Required]
        public bool GIOI_TINH { get; set; }
        public string? NGHE_NGHIEP { get; set; }
        public string? SDT { get; set; }
        public string? DIA_CHI { get; set; }
        public string? CCCD { get; set; }
    }
}
