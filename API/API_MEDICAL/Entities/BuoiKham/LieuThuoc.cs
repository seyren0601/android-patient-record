using Microsoft.EntityFrameworkCore;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace API_MEDICAL.Entities.BuoiKham
{
    [Table("LIEU_THUOC", Schema="patient_record")]
    [PrimaryKey("ID_BUOI_KHAM", "ID_THUOC")]
    public class LieuThuoc
    {
        [ForeignKey("BuoiKham")]
        [Required]
        public int ID_BUOI_KHAM { get; set; }
        [ForeignKey("Thuoc")]
        [Required]
        public string ID_THUOC { get; set; }
        public int LIEU_SANG { get; set; }
        public int LIEU_TRUA { get; set; }
        public int LIEU_CHIEU { get; set; }
        public int LIEU_TOI { get; set; }
    }
}
