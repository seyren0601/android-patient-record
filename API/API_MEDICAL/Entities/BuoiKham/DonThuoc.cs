using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace API_MEDICAL.Entities.BuoiKham
{
    [Table("DON_THUOC", Schema="patient_record")]
    public class DonThuoc
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int ID { get; set; }
        public int SO_NGAY_LAY_THUOC { get; set; }
    }
}
