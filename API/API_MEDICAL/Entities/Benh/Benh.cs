using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace API_MEDICAL.Entities
{
    [Table("BENH", Schema="PATIENT_RECORD")]
    public class Benh
    {
        [Key]
        [StringLength(50)]
        public string MA_BENH { get; set; }
        public string TEN_BENH { get; set; }
        [StringLength(50)]
        [ForeignKey("MA_BENH")]
        public string MA_LOAI_BENH { get; set; }
    }
}
