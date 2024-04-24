using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace API_MEDICAL.Entities
{
    [Table("LOAI_BENH", Schema="PATIENT_RECORD")]
    public class LoaiBenh
    {
        [Key]
        [StringLength(50)]
        public string MA_LOAI { get; set; }
        public string TEN_LOAI { get; set; }
        [StringLength(50)]
        [ForeignKey("NhomBenh")]
        public string NHOM_BENH { get; set; }
        ICollection<Benh> DanhSach_Benh { get; set; }
    }
}
